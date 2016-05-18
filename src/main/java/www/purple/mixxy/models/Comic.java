package www.purple.mixxy.models;

import com.github.slugify.Slugify;
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

import java.io.IOException;
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
	public String sluggedTitle;
	
	public String message;
	public String signature;
	public long timestamp;
	public String disqusKey;
	
	@Unindex
	public String description;
	public String series;
	public List<String> tags;
	public Date createdAt;
	public Date updatedAt;
	
	public Long authorId;

	public Comic() {/* needed by Objectify */ }

	public Comic(final Comic ancestorComic, final User author, final String title, final String description, final String series, final List<String> tags) {
		
		if (ancestorComic == null){
			this.ancestorComicId = Lists.newArrayList();
		} 
		else {
			this.ancestorComicId = ancestorComic.ancestorComicId;
		}
		this.authorId = author.id;
		
		Slugify slg;
		try {
			slg = new Slugify();
			this.sluggedTitle = slg.slugify(title); 
		} catch (IOException e) {
		    // NOTE: Unlikely to occur; if it does, there's likely a bigger problem.
		    // Slugify reads from a .properties file that is included in its own resources,
		    // and you can't override it (so no chance of misuse).
			e.printStackTrace();
		}
		
		this.title = title;
		this.description = description;
		this.series = series;
		this.tags = tags;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

}
