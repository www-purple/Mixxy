package www.purple.mixxy.controllers;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import www.purple.mixxy.filters.UrlNormalizingFilter;

import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class SearchController {

  public Result search(@PathParam("id") Long id, Context context) {
    return Results.TODO();
  }
}
