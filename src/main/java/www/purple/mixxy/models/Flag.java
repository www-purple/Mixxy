package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Flag {
	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	@Load
	public Ref<User> flagger;
	@Load
	public Ref<User> flaggedUser;
	
	public String reason;

	public Date createdAt;
	
	public Flag() {/* Needed by Objectify */}

	public Flag(final String reason) {
		/* Needed by Objectify */
		this.reason = reason;
		this.createdAt = new Date();
	}
}
