package www.purple.mixxy.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.FilterWith;
import ninja.appengine.AppEngineFilter;
import www.purple.mixxy.dao.ImageDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ImageController {
	
	@Inject
	private ImageDao imageDao;

}
