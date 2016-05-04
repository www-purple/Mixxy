package www.purple.mixxy.models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ImageData {
	
	@Id
	public Long id;
	
	@Index
	public Long comicId;
	
	public byte[] imageBlob;
	
	public Date createdAt;
	public Date updatedAt;
	
	public ImageData () {/* needed by Objectify */}
	
	public ImageData(final Long id, final byte[] imageBlob){
		this.comicId = id;
		this.imageBlob = imageBlob;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

}
