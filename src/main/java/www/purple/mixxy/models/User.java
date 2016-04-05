package www.purple.mixxy.models;

import com.google.appengine.api.oauth.OAuthService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Index
public class User {
    
    @Id
    public Long id;
    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public String email;
    public Map<OAuthService, String> authentications = new HashMap<>();

    public Role role;
    public Date createdAt;
    public Date updatedAt;
    public Boolean isActive;

    public User() { /* Needed by Objectify */ }
    
    public User(final String username,
                final String password,
                final String firstname,
                final String lastname,
                final String email) {

        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        // @Chris - TODO: how are we going to use this map?
        //this.authentications =

        this.role = Role.USER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isActive = true;

    }
 
}
