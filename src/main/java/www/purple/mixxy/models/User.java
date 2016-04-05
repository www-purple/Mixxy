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
@Index
public class User {

	@Id
	public Long id;
	public String username;
	public String firstname;
	public String lastname;
	public String email;

	// @Chris - TODO: how are we going to use this map?
	// public Map<OAuthService, String> authentications = new HashMap<>();

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
