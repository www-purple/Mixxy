package www.purple.mixxy.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.User;

/**
 * Provide common operations for creating and managing {@link User}s.
 *
 * @see User
 *
 * @author Brian Sabzjadid
 */
@Singleton
public class UserDao {

  @Inject
  private Provider<Objectify> objectify;

  /**
   * @param username
   *          The name of the user whose existence is to be queried.
   * @return True if a user with this name actually exists.
   * @TODO Can be optimized, maybe with a projection query or something.
   */
  public boolean isUserValid(String username) {

    if (username != null) {

      User user = objectify.get().load().type(User.class)
          .filter(User.USERNAME, username).first().now();

      if (user != null) {
        // If a user with this name actually exists...
        return true;
      }
    }

    return false;
  }

  /**
   * Given a {@code username}, get the underlying {@link User}.
   *
   * @param username
   *          The user whose details should be retrieved.
   * @return The {@link User} if it exists, {@code null} if doesn't or if {@code username} is
   *         {@code null}.
   */
  public User getUser(String username) {
    User user = null;

    if (username != null) {
      // If we were given a valid username...
      user = objectify.get().load().type(User.class)
          .filter(User.USERNAME, username).first().now();
    }

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

  public void createUser(String username, String firstName, String lastName, String gender,
      String email, String pictureUrl, String locale, String providerId, String provider) {

    User user = new User(username, firstName, lastName, gender, email,
        pictureUrl, locale, providerId, provider);
    objectify.get().save().entity(user).now();
  }

  public boolean updateUser(String username, User info) {
    return false;
  }
}
