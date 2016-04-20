package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@SuppressWarnings("PMD.ShortClassName")
@Entity
public class Ban {
	@Id
	public Long id;

	// can refer to these Entities using the already fetched Comic, this solve
	// performance problems
	// See: https://github.com/objectify/objectify/wiki/Entities#load
	public Long offenderId;
	public Long victimId;
	public Long modId;
	
	public String reason;

	public Date bannedOn;
	public Date expires;
	
	public Ban() {/* Needed by Objectify */}

	public Ban(final User offender, final User victim, final User mod, final String reason, final Date banExpiration) {
		/* Needed by Objectify */
		this.offenderId = offender.id;
		this.victimId = victim.id;
		this.modId = mod.id;
		this.reason = reason;
		this.bannedOn = new Date();
		this.expires = banExpiration;
	}
}
