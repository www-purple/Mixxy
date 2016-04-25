package www.purple.mixxy.controllers;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.etc.LoggedInUser;
import www.purple.mixxy.filters.JsonEndpoint;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ComicDto;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ComicController {

	@Inject
	private ComicDao comicDao;

	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	public Result remix() {
		return Results.TODO();
	}

	public Result edit() {
		return Results.TODO();
	}

	public Result comicShow(@PathParam("id") Long id) {

		Comic comic = null;

		if (id != null) {

			comic = comicDao.getComic(id);

		}

		return Results.html().render("comic", comic);

	}

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

		} else {

			comicDao.newComic(username, comicDto);

			context.getFlashScope().success("New article created.");

			return Results.redirect("/");

		}

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
