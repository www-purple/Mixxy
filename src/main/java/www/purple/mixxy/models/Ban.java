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

public class Ban {
	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	@Load
	public Ref<User> offender;
	@Load
	public Ref<User> victim;
	@Load
	public Ref<User> mod;
	
	public String reason;

	public Date bannedOn;
	public Date expires;
	
	public Ban() {/* Needed by Objectify */}

	public Ban(final String reason, final Date banExpiration) {
		/* Needed by Objectify */
		this.reason = reason;
		this.bannedOn = new Date();
		this.expires = banExpiration;
	}
}
