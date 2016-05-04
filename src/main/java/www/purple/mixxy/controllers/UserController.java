package www.purple.mixxy.controllers;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;

import com.google.inject.Singleton;
/**
 * 
 * @author Jesse Talavera-Greenberg
 *
 */
@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class UserController {

  @FilterWith(JsonEndpoint.class)
  public Result user(
          @PathParam("user") String user) {
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
