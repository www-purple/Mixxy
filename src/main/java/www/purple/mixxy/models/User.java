package www.purple.mixxy.models;

/**
 * Modified by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class User {
	
	@Id
	public Long id;
	@Index
	public String username;
	public String firstname;
	public String lastname;
	public String email;

	// @Chris - TODO: how are we going to use this map?
	// public Map<OAuthService, String> authentications = new HashMap<>();

	
	//TODO: Partial Indexing of MOD roles
	// https://github.com/objectify/objectify/wiki/Queries#custom-conditions
	
//	static class IfMod extends ValueIf<User> {
//	    public boolean matches(User user) {
//	        return user.role == Role.MODERATOR;
//	    }
//
//		@Override
//		public boolean matchesValue(User arg0) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//	}
	
	//TODO: Partial Indexing of MOD roles
	//@Index(IfMod.class)
	public Role role;
	
	public Date createdAt;
	public Date updatedAt;
	public Boolean isActive;

	public User() {/* Needed by Objectify */ }

	public User(final String username, final String firstname, final String lastname,
			final String email) {

		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;

		// @Chris - TODO: how are we going to use this map?
		// this.authentications =

		this.role = Role.USER;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.isActive = true;

	}
	
}
