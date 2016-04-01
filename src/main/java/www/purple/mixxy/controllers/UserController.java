package www.purple.mixxy.controllers;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import www.purple.mixxy.etc.UserParameter;
import www.purple.mixxy.filters.JsonEndpoint;

import com.google.inject.Singleton;

/**
 * 
 * @author Jesse Talavera-Greenberg
 *
 */
@Singleton
@FilterWith(AppEngineFilter.class)
public class UserController {

  @FilterWith(JsonEndpoint.class)
  public Result user(@UserParameter String user, Context context) {
    return Results.TODO().render("user", user);
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
  public Result work() {
    return Results.TODO();
  }
}
