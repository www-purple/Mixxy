package www.purple.mixxy.controllers;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import www.purple.mixxy.filters.JsonEndpoint;

import com.google.inject.Singleton;

@Singleton
@FilterWith(AppEngineFilter.class)
public class UserController {

  @FilterWith(JsonEndpoint.class)
  public Result user(@PathParam("id") Long id, Context context) {
    return Results.ok().render("id", id);
  }
}
