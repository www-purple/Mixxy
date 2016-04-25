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
	@FilterWith(JsonEndpoint.class)
	public Result remixShow(@PathParam("id") Long id) {
		Comic remix = null;

		if (id != null) {

			remix = comicDao.getRemix(id);

		}

		return Results.html().render("remix", remix);

	}
	
	/**
	 * This method creates a new remix for a specific comic.
	 * 
	 * Pass in the id of the parent comic to remix on along with the current user, context and data model information.
	 * 
	 * @param id Identifier of the Parent comic
	 * @param username Current user's username
	 * @param context html context
	 * @param comicDto Data model for the comic object
	 * @param validation Checks for various violations such as:  field violations (on controller method fields), 
	 * bean violations (on an injected beans field) or general violations (deprecated)
	 * 
	 * @return resulting route to redirect with content
	 */
	@FilterWith(JsonEndpoint.class)
	public Result newRemix(@PathParam("id") Long id, @LoggedInUser String username, Context context,
			@JSR303Validation ComicDto comicDto, Validation validation) {

		if (validation.hasViolations()) {

			context.getFlashScope().error("Please correct field.");
			context.getFlashScope().put("title", comicDto.title);
			context.getFlashScope().put("description", comicDto.description);
			context.getFlashScope().put("images", comicDto.images);
			context.getFlashScope().put("likes", comicDto.likes);
			context.getFlashScope().put("tags", comicDto.tags);

			return Results.redirect("/remix/");

		} else {

			comicDao.branchComic(username, id);

			context.getFlashScope().success("New remix created.");

			return Results.redirect("/");

		}

	}
	
	@FilterWith(JsonEndpoint.class)
	public Result comicShow(@PathParam("id") Long id) {

		Comic comic = null;

		if (id != null) {

			comic = comicDao.getComic(id);

		}

		return Results.html().render("comic", comic);

	}

	/**
	 * This method creates a new comic.
	 * 
	 * Pass in the current user, context and data model information.
	 * 
	 * @param username Current user's username
	 * @param context html context
	 * @param comicDto Data model for the comic object
	 * @param validation Checks for various violations such as:  field violations (on controller method fields), 
	 * bean violations (on an injected beans field) or general violations (deprecated)
	 * 
	 * @return resulting route to redirect with content
	 */
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

			context.getFlashScope().success("New comic created.");

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
