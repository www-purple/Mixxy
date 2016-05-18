package www.purple.mixxy.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

  @Inject
  private Provider<Objectify> objectify;

  /**
   * @param username
   *          The name of the user whose existence is to be queried.
   * @return True if a user with this name actually exists.
   * @TODO Can be optimized, maybe with a projection query or something.
   */
  public User isUserValid(String username) {

    if (username != null) {

      User user = objectify.get().load().type(User.class)
          .filter(User.USERNAME, username).first().now();

      if (user != null) {
        // If a user with this name actually exists...
        return user;
      }
    }

    return null;
  }

  /**
   * Given a username, get the underlying {@link User}.
   * 
   * @param username
   *          The user whose details should be retrieved.
   * @return The {@link User} if it exists, {@code null} if doesn't or if
   *         {@code username} is {@code null}.
   */
  public User getUser(String username) {  
	  
	  if(username != null) {
		  User user = objectify.get().load().type(User.class)
				  .filter(User.USERNAME, username).first().now();
		  
		  if(user != null) return user;
	  }
	  
	  return null;
  }
  
  public User createUser(String username, String firstName, String lastName, String gender,
                         String email, String pictureUrl, String locale, String providerId, String provider) {
	  
	  User user = new User(username, firstName, lastName, gender, email, 
			  pictureUrl, locale, providerId, provider);
	  objectify.get().save().entity(user).now();
	  
	  return user;
  }

  /**
   * Given a user ID, get the underlying {@link User}.
   * 
   * @param id
   *          The ID user whose details should be retrieved.
   * @return The {@link User} if it exists, {@code null} if doesn't.
   */
  public User getUser(long id) {
    return objectify.get().load().type(User.class).id(id).now();
  }
  
  public boolean updateUser(String username, User info) {
    return false;
  }
}
