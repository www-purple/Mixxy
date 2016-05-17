package www.purple.mixxy.controllers;

import com.google.inject.Inject;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.etc.UserParameter;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;

import com.google.inject.Singleton;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jesse Talavera-Greenberg
 *
 */
@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class UserController {

  @Inject
  private ComicDao comicDao;

  @Inject
  private UserDao userDao;

  @FilterWith(JsonEndpoint.class)
  public Result user(
          @PathParam("user") String user, Context context) {

    User username = userDao.getUser(user);
    if (username == null) {
      context.getFlashScope().error("Invalid user.");
      return Results.redirect("/");
    }
      List<Comic> comics = comicDao.getComics(user);
      List<String> series = new ArrayList();

    // stringify the series
    for (Comic comic: comics) {
      if (comic.series != null && !series.contains(comic.series)) series.add(comic.series);
    }

      return Results.html().render("user", username.username).render("userPhoto", username.pictureUrl)
              .render("comics", comics).render("series", series);
  }
  
  @FilterWith(JsonEndpoint.class)
  public Result settings() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result subscribe() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result unsubscribe() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result subscribed(
          @PathParam("user") String user, Context context) {

    User username = userDao.getUser(user);
    if (username == null) {
      context.getFlashScope().error("Invalid user.");
      return Results.redirect("/");
    }

    return Results.html().render("user", username.username);
  }
  
  @FilterWith(JsonEndpoint.class)
  public Result subscribers() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result like() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result unlike() {
    return Results.TODO();
  }
  
  @FilterWith(JsonEndpoint.class)
  public Result likes() {
    return Results.TODO();
  }
  
  @FilterWith(JsonEndpoint.class)
  public Result works() {
    return Results.TODO();
  }
  
  @FilterWith(JsonEndpoint.class)
  public Result work(
          @PathParam("user") String user,
          @PathParam("work") String work) {
      return Results.html().render("user", user).render("work", work);
  }
}
