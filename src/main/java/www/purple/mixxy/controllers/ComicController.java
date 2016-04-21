package www.purple.mixxy.controllers;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import www.purple.mixxy.dao.ArticleDao;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ComicController {

	@Inject
    private ComicDao comicDao;
	
  public Result remix() {
    return Results.TODO();
  }

  public Result edit() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result newWork() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result likes() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result remixes() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result root() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result parent() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result delete() {
    return Results.TODO();
  }

  @FilterWith(JsonEndpoint.class)
  public Result update() {
    return Results.TODO();
  }
}
