package www.purple.mixxy.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotDefault;

/**
 * Representation of a user of this application.
 *
 * @author Brian Sabzjadid
 */
@Entity
public class User {
  /**
   * Name of the {@link #username username} field in the Data Store.
   */
  public static final String USERNAME = "username";

  /**
   * Unique identifier for this {@link User}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  /**
   * {@link User}-determined name to publicly identify as. <b>May be changed at any time.</b>
   */
  @Index
  public String username;

  /**
   * The first name of this {@link User}.
   *
   * @TODO: Do we really need a first and a last name? Why not just one {@code name} field?
   * @see #lastname
   */
  public String firstname;

  /**
   * The last name of this {@link User}.
   *
   * @TODO: Do we really need a first and a last name? Why not just one {@code name} field?
   * @see #firstname
   */
  public String lastname;

  /**
   * The gender that user identifies as.
   *
   * @TODO Can we come up with an {@code enum} for this or something? Also, it's 2016, so we can't
   *       assume that this will be Male or Female any more.
   */
  public String gender;

  /**
   * The e-mail through which this {@link User} will receive site communications.
   */
  public String email;

  /**
   * URL of this {@link User}'s avatar.
   *
   * @TODO Can we just embed this directly?
   */
  public String pictureUrl;

  /**
   * Location of this {@link User} on Earth.
   *
   * @TODO Is there an {@code enum} or something we can wrap this in?
   */
  public String locale;

  /**
   * Something to do with authentications. I need to verify with Brian or Cris what exactly is in
   * here.
   */
  public Map<String, String> authentications = new HashMap<>();

  /**
   * The {@link Role} this user plays in the site's community. Only indexed if it's anything besides
   * {@link Role#USER}.
   */
  @Index(IfNotDefault.class)
  public Role role = Role.USER;

  /**
   * The {@link Date} on which this {@link User} registered.
   */
  public Date createdAt;

  /**
   * The {@link Date} on which this {@link User} last updated their account information.
   *
   * @TODO: What does it mean to "update"? Log on? Change personal info? Submit a comic? Write a
   *        comment?
   */
  public Date updatedAt;

  /**
   * False if this {@link User} has deactivated their account for whatever reason. They could still
   * come back!
   */
  public Boolean isActive;

  public User() {
    /* Needed by Objectify */
  }

  public User(String username, final String firstname, final String lastname, final String gender,
      final String email, final String pictureUrl, String locale, final String providerId,
      final String provider) {

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
