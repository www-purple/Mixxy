package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

public class Like {
	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	@Load
	public Ref<Comic> comic;
	@Load
	public Ref<User> user;

	public Date createdAt;

	public Like() {
		/* Needed by Objectify */
		this.createdAt = new Date();
	}
}
