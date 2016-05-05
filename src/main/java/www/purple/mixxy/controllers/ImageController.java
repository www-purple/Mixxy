package www.purple.mixxy.controllers;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.utils.NinjaConstant;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.dao.ImageDao;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ImageData;
import www.purple.mixxy.models.User;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ImageController {
	
	@Inject
	private ImageDao imageDao;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private ComicDao comicDao;
	
	public Result image(@PathParam("user") String user, @PathParam("work") String work) {
	    User author = userDao.getUser(user);
		Comic comic = comicDao.getComic(author, work);
		
		if (author == null || comic == null) {
			  return Results.notFound().template("www/purple/mixxy/" + NinjaConstant.LOCATION_VIEW_FTL_HTML_NOT_FOUND);
		}
		
		ImageData image = imageDao.getImageForComic(comic.id); 

		return Results.ok().renderRaw(image.imageBlob).supportedContentTypes("image/gif", "image/png", "image/jpeg");
	}

}
