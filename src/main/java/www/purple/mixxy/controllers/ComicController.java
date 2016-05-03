package www.purple.mixxy.controllers;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.utils.NinjaConstant;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.etc.LoggedInUser;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ComicDto;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.User;

/**
 * Behavior pertaining to the creation, retrieval, deletion, and management of {@link Comic}s.
 *
 * @see Comic
 *
 * @author Jesse Talavera-Greenberg
 * @author Brian Sabzjadid
 */
@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ComicController {

  @Inject
  private ComicDao comicDao;

  @Inject
  private UserDao userDao;

  /**
   * Creates a new remix for a specific {@link Comic}.
   *
   * @param user
   *          Author of the originating {@link Comic} (which itself may be a remix).
   * @param slug
   *          Slug-cased title of the originating {@link Comic} (which itself may be a remix).
   * @param context
   *          html context
   * @param comicDto
   *          Data model for the comic object
   * @param validation
   *          Checks for various violations such as: field violations (on controller method fields),
   *          bean violations (on an injected beans field) or general violations (deprecated)
   *
   * @return resulting route to redirect with content
   */
  @FilterWith(JsonEndpoint.class)
  public Result newRemix(@PathParam("user") String user, @PathParam("work") String slug, Context context,
      @JSR303Validation ComicDto comicDto, Validation validation) {

    if (validation.hasViolations()) {

      context.getFlashScope().error("Please correct field.");
      context.getFlashScope().put("title", comicDto.title);
      context.getFlashScope().put("description", comicDto.description);
      context.getFlashScope().put("images", comicDto.images);
      context.getFlashScope().put("likes", comicDto.likes);
      context.getFlashScope().put("tags", comicDto.tags);

      return Results.redirect("/remix/");

    }
    else {

      Comic comic = null;

      if (slug != null) {

        comic = comicDao.getComic(user, slug);

      }

      if (comic == null) {
        return Results.notFound();
      }

      comicDao.branchComic(user, comic.id);

      context.getFlashScope().success("New remix created.");

      return Results.redirect("/");

    }

  }

  // TODO: Move image-loading logic to here
  @FilterWith(JsonEndpoint.class)
  public Result comic(@PathParam("user") String user, @PathParam("work") String work) {

    User author = userDao.getUser(user);
    Comic comic = comicDao.getComic(author, work);

    if (author == null || comic == null) {
      return Results.notFound()
          .template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
    }

    return Results.ok().render("comic", comic).render("author", author).html();

  }

  /**
   * Creates a new comic.
   *
   * @param username
   *          Author of the new {@link Comic} (the current user).
   * @param context
   *          html context
   * @param comicDto
   *          Data model for the {@link Comic}
   * @param validation
   *          Checks for various violations such as: field violations (on controller method fields),
   *          bean violations (on an injected beans field) or general violations (deprecated)
   *
   * @return resulting route to redirect with content
   */
  @FilterWith(JsonEndpoint.class)
  public Result newWork(@LoggedInUser String username, Context context, @JSR303Validation ComicDto comicDto,
      Validation validation) {

    if (validation.hasViolations()) {

      context.getFlashScope().error("Please correct field.");
      context.getFlashScope().put("title", comicDto.title);
      context.getFlashScope().put("description", comicDto.description);
      context.getFlashScope().put("images", comicDto.images);
      context.getFlashScope().put("likes", comicDto.likes);
      context.getFlashScope().put("tags", comicDto.tags);

      return Results.redirect("/create/");

    }
    else {

      boolean didCreateComic = false;

      didCreateComic = comicDao.newComic(username, comicDto);

      if (!didCreateComic) {
        return Results.notFound();
      }

      context.getFlashScope().success("New comic created.");

      return Results.redirect("/");

    }

  }

  @FilterWith(JsonEndpoint.class)
  public Result likes(@PathParam("user") String username, @PathParam("work") String slug) {
    List<Like> likes = null;

    if (slug != null) {

      likes = comicDao.getLikes(username, slug);

    }

    if (likes == null) {
      return Results.notFound();
    }

    return Results.html().render("likes", likes);
  }

  @FilterWith(JsonEndpoint.class)
  public Result remixes(@PathParam("user") String username, @PathParam("work") String slug) {
    List<Comic> comics = null;

    if (slug != null) {
      comics = comicDao.getRemixes(username, slug);
    }

    if (comics == null) {
      return Results.notFound();
    }

    return Results.html().render("comics", comics);
  }

  @FilterWith(JsonEndpoint.class)
  public Result delete(@PathParam("user") String username, @PathParam("work") String slug, Context context) {

    boolean didDeleteComic = false;
    didDeleteComic = comicDao.deleteComic(username, slug);

    if (didDeleteComic) {
      return Results.notFound();
    }

    context.getFlashScope().success("Deleted comic.");

    return Results.redirect("/");
  }

  @FilterWith(JsonEndpoint.class)
  public Result update(@PathParam("user") String username, @PathParam("work") String slug, Context context) {

    boolean didSaveComic = false;
    didSaveComic = comicDao.saveComic(username, slug);

    if (didSaveComic) {
      return Results.notFound();
    }

    context.getFlashScope().success("Saved comic.");

    return Results.redirect("/");
  }

  /**
   * Returns the {@link Result} containing the actual image data for a {@link Comic}. The lack of a
   * {@link JsonEndpoint} annotation is intentional. Should be accessible through the {@code /api/}
   * prefix for consistency, but the behavior for this case is identical, unlike that of the other
   * routes. An image can be accessed with a plain old HTTP request, and will be returned to the
   * client as its constituent bytes (as opposed to some encoding like base64).
   *
   * <p>
   * Whether this image comes from DeviantArt or from Mixxy should be irrelevant to the user of this
   * route.
   * </p>
   *
   * @param user
   *          The author of the desired {@link Comic}.
   * @param slug
   *          The slug-cased title of the desired {@link Comic}.
   *
   * @return A {@link Result} containing the relevant {@link Comic}'s image data.
   */
  public Result image(@PathParam("user") String user, @PathParam("work") String slug) {

    User author = userDao.getUser(user);
    Comic comic = comicDao.getComic(author, slug);

    if (comic != null) {
      // If this Comic actually exists...
      return Results.redirectTemporary(
          "https://www.cs.stonybrook.edu/sites/default/files/wwwfiles/mckenna_0.jpg")
          .supportedContentTypes("image/gif", "image/png", "image/jpeg");
    }
    else {
      return Results.notFound()
          .template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
    }
  }

  @FilterWith(JsonEndpoint.class)
  public Result root() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result parent() {
    return Results.TODO();
  }

  public Result muro() {
    return Results.ok().html();
  }

  public Result upload() {
    return Results.ok().html();
  }
}
