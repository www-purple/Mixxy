package www.purple.mixxy.controllers;

import com.google.inject.Singleton;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;

import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.User;

/**
 * Handles management and retrieval of {@link User}s. For information pertaining to the
 * {@link Comic}s authored by a {@link User}, please see {@link ComicController}.
 *
 * @see User
 * @see ComicController
 *
 * @author Jesse Talavera-Greenberg
 * @author Brian Sabzjadid
 */
@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class UserController {

  @FilterWith(JsonEndpoint.class)
  public Result user(@PathParam("user") String user) {
    return Results.html().render("user", user);
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
  public Result subscribed() {
    return Results.TODO();
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
  public Result comics() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result comic(@PathParam("user") String user, @PathParam("comic") String comic) {
    return Results.html().render("user", user).render("comic", comic);
  }
}
