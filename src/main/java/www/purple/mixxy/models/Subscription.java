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

public class Subscription {
	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	@Load
	public Ref<User> subscriber;
	@Load
	public Ref<User> publisher;

	public Date createdAt;

	public Subscription() {
		/* Needed by Objectify */
		this.createdAt = new Date();
	}
}
