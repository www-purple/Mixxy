package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

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
	@Load
	public Ref<User> author;
	@Load
	public Ref<Comic> parent;

	@Index
	public String title;
	public String description;
	public List<String> tags;
	public int version;

	public Date createdAt;
	public Date updatedAt;

	public Comic() {/* needed by Objectify */ }

	public Comic(final String title, final String description, final List<String> tags, final int version) {

		this.title = title;
		this.description = description;
		this.tags = tags;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.version = version;
	}

}
