package www.purple.mixxy.models;

/**
 * Modified by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotDefault;

import java.util.*;

@Entity
public class User {
	public static final String USERNAME = "username";
	
	@Id
	public Long id;
	
	@Index
	public String username;
	public String firstname;
	public String lastname;
	public String gender;
	public String email;
	public String pictureUrl;
	public String locale;

	public Map<String, String> authentications = new HashMap<>();

	@Index(IfNotDefault.class)
	public Role role = Role.USER;
	
	public Date createdAt;
	public Date updatedAt;
	public Boolean isActive;

	public User() {/* Needed by Objectify */ }

	public User(final String username, final String firstname, final String lastname, final String gender,
			final String email, final String pictureUrl, String locale, final String providerId, final String provider) {

		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.email = email;
		this.pictureUrl = pictureUrl;
		this.locale = locale;

		this.authentications.put(provider, providerId);

		this.role = Role.USER;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.isActive = true;
	}

}
