package www.purple.mixxy.models;

import com.google.common.collect.Lists;
/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import java.util.Date;
import java.util.List;

@Entity
@Index
public class Comic {

	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	//@Load
	//public Ref<User> author;
	
	public List<Long> ancestorComicId;

	public String title;
	
	@Unindex
	public String description;
	public List<String> tags;

	public Date createdAt;
	public Date updatedAt;
	
	public List<Long> authorIds;

	public Comic() {/* needed by Objectify */ }

	public Comic(final Comic ancestorComic, final User author, final String title, final String description, final List<String> tags) {
		
		if (ancestorComic == null){
			this.ancestorComicId = Lists.newArrayList();
		} 
		else {
			this.ancestorComicId = ancestorComic.ancestorComicId;
		}
		this.authorIds = Lists.newArrayList(author.id);
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

}
