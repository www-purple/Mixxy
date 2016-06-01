package www.purple.mixxy.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.Param;
import ninja.params.Params;
import ninja.params.PathParam;
import ninja.utils.NinjaConstant;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.etc.LoggedInUser;
import www.purple.mixxy.etc.UserParameter;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.helpers.ApiKeys;
import www.purple.mixxy.helpers.DisqusSSOHelper;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ComicDto;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ComicController {

	@Inject
	private ComicDao comicDao;

	@Inject
	private UserDao userDao;
	
	@Inject
    private ApiKeys apiKeys;

	/**
	 * This method creates a new remix for a specific comic.
	 * 
	 * Pass in the id of the parent comic to remix on along with the current
	 * user, context and data model information.
	 * 
	 * @param id
	 *            Identifier of the Parent comic
	 * @param username
	 *            Current user's username
	 * @param context
	 *            html context
	 * @param comicDto
	 *            Data model for the comic object
	 * @param validation
	 *            Checks for various violations such as: field violations (on
	 *            controller method fields), bean violations (on an injected
	 *            beans field) or general violations (deprecated)
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
			context.getFlashScope().put("image", comicDto.image);
			context.getFlashScope().put("likes", comicDto.likes);
			context.getFlashScope().put("tags", comicDto.tags);

			return Results.redirect("/remix/");

		} else {

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
	public Result comic(@PathParam("user") String username, @PathParam("work") String work, Context context) {

	    User author = userDao.getUser(username);
		Comic comic = comicDao.getComic(author, work);


		if (author == null || comic == null) {
		  return Results.notFound().template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
		}
		
		// This sets up single sign on on disqus
		if(context.getSession().get("username") != null) {
			
			User user = null;
			
			if((user = userDao.isUserValid(context.getSession().get("username"))) != null) {
				// Get SSO message body
				DisqusSSOHelper sso = new DisqusSSOHelper(
						(user.id).toString(), 
						user.username, 
						user.email,
						user.pictureUrl,
						apiKeys.getDisqusSecret()
				);
				
				comic.message = sso.base64EncodedStr;
				comic.signature = sso.signature;
				comic.timestamp = sso.timestamp;
				comic.disqusKey = apiKeys.getDisqusKey();
			}
			
			System.out.println("USER: " + user);
		} else {
			comic.message = "notloggedin";
			comic.signature = "notloggedin";
			comic.timestamp = -1;
			comic.disqusKey = "notloggedin";
		}
		
		return Results.ok().render("comic", comic).render("user", author).html();
	}

	/**
	 * This method creates a new comic.
	 * 
	 * Pass in the current user, context and data model information.
	 * 
	 * @param username
	 *            Current user's username
	 * @param context
	 *            html context
	 * @param comicDto
	 *            Data model for the comic object
	 * @param validation
	 *            Checks for various violations such as: field violations (on
	 *            controller method fields), bean violations (on an injected
	 *            beans field) or general violations (deprecated)
	 * 
	 * @return resulting route to redirect with content
	 */
	@FilterWith(JsonEndpoint.class)
	public Result newWork(@LoggedInUser String username, Context context, @JSR303Validation ComicDto comicDto,
			Validation validation) {

		if (username == null){
			context.getFlashScope().error("Must be signed in to draw.");
			return Results.redirect("/");

		}
			User user = userDao.getUser(username);
			List<Comic> comics = comicDao.getComics(username);
			List<String> series = new ArrayList();

			// stringify the series
			for (Comic comic: comics) {
				if (comic.series != null && !series.contains(comic.series)) {
					series.add(comic.series);
					System.out.println(comic.series);
				}

			}
			
			// so we can render the series in the list of series options
			return Results.html().render("series", series);
	}
	

	/**
	 * This method creates a new comic.
	 *
	 * Pass in the current user, context and data model information.
	 *
	 * @param username
	 *            Current user's username
	 * @param context
	 *            html context
	 *
	 * @return resulting route to redirect with content
	 */
	@FilterWith(JsonEndpoint.class)
	public Result postWork(@LoggedInUser String username, Context context, @Param("muroimage") String muroimage,
						   @Param("title") String title,
						   @Param("description") String description,
						   @Param("series") String series,
						   @Params("tags") String[] tags,
						   @Param("nsfw") boolean nsfw) {

		if (username == null){
			context.getFlashScope().error("Must be signed in to save comic.");
			return Results.redirect("/");

		}
		
		ComicDto comicDto = new ComicDto();
		
		// If Base64 image is invalid, send a bad request
		if( !muroimage.matches("data\\:image\\/(png|jpe?g|gif)\\;base64,.+")){
			return Results.badRequest();
		}
		
		//Strip Bsae64 header 
		String replacedMuroImage = muroimage.replaceAll("data\\:image\\/(png|jpe?g|gif)\\;base64,", "");
		System.out.println(replacedMuroImage);
		
		byte[] imgInByteArr = Base64.decodeBase64(replacedMuroImage);
		
		Image image = ImagesServiceFactory.makeImage(imgInByteArr);
		
		comicDto.image = image;
		comicDto.title = title;
		comicDto.description = description;
		comicDto.series = series;
		comicDto.tags = new ArrayList<>();
		if (tags != null){
			for (String tag: tags) {
				comicDto.tags.add((tag));
	
			}
		}
		// does this work?
		if (nsfw) comicDto.tags.add("18+");

		if (!(comicDao.newComic(username, comicDto))){
			context.getFlashScope().error("Must have some content to upload.");
			return Results.redirect("/");
		}

		context.getFlashScope().success("New comic created.");
		return Results.redirect("/");
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
		
		if (didDeleteComic == false){
			return Results.notFound();
		}

		context.getFlashScope().success("Deleted comic.");

		return Results.redirect("/");
	}

	@FilterWith(JsonEndpoint.class)
	public Result update(@PathParam("user") String username, @PathParam("work") String slug, Context context) {
		
		boolean didSaveComic = false;
		didSaveComic = comicDao.saveComic(username, slug);

		if (didSaveComic == false){
			return Results.notFound();
		}
		
		context.getFlashScope().success("Saved comic.");

		return Results.redirect("/");
	}

	/**
	 * Returns the {@link Result} containing the actual image data for a {@link Comic}.
	 * May be accessed through the {@code /api/} prefix for consistency, but the
	 * behavior for this case is identical, unlike that of the other routes.
	 * An image can be accessed with a plain old HTTP request, and will be returned
	 * to the client as its constituent bytes (as opposed to some encoding like base64).
	 *
	 * Whether this image comes from DeviantArt or from Mixxy should be irrelevant
	 * to the user of this route.
	 *
	 * @return A {@link Result} containing the relevant {@link Comic}'s image data.
	 */
	public Result image(@PathParam("user") String user, @PathParam("work") String slug) {

      User author = userDao.getUser(user);
	  Comic comic = comicDao.getComic(author, slug);

	  if (comic != null) {
	    // If this Comic actually exists...
  	    return Results.redirectTemporary("https://www.cs.stonybrook.edu/sites/default/files/wwwfiles/mckenna_0.jpg")
  	      .supportedContentTypes("image/gif", "image/png", "image/jpeg");
	  }
	  else {
	    return Results.notFound().template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
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

	// go to series page
	@FilterWith(JsonEndpoint.class)
	public Result series(@PathParam("user") String user, @PathParam("series") String series) {

		User username = userDao.getUser(user);
		List<Comic> comics = comicDao.getSeries(user, series);


		if (username == null || comics == null) {
			return Results.notFound().template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
		}

		return Results.ok().render("comics", comics).render("user", user).html().render("series", series);
	}
	
}
