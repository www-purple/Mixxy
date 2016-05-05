package www.purple.mixxy.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ImageData;

public class ImageDao {

	@Inject
	private Provider<Objectify> objectify;

	public ImageData getImageForComic(long id) {
		return objectify.get().load().type(ImageData.class).id(id).now();
	}
	
	public boolean saveImage(Comic comic, byte[] imageBlob) {
		
		if (comic == null || imageBlob == null) {
			return false;
		}
		
		ImageData image = new ImageData(comic.id, imageBlob);
		
		objectify.get().save().entity(image).now();

		return true;
	}

	public boolean deleteImage(long id) {

		ImageData image = getImageForComic(id);
		
		if(image == null){
			return false;
		}
		
		objectify.get().delete().entity(image);

		return true;
	}

}
