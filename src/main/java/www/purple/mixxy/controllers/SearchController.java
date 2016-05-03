package www.purple.mixxy.controllers;

import com.google.inject.Singleton;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;

import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;

/**
 * Controls qualified retrieval of {@link Comic}s.
 */
@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class SearchController {

  public Result search(@PathParam("id") Long id, Context context) {
    return Results.ok().html();
  }

  public Result upload() {
    return Results.ok().html();
  }
}
