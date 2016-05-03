package www.purple.mixxy.models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Models an instance of disciplinary action by a moderator towards a {@link User}.
 *
 * @see User
 *
 * @author Brian Sabzjadid
 */
@SuppressWarnings("PMD.ShortClassName")
@Entity
public class Ban {

  /**
   * Unique identifier for this {@link Ban}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  // See: https://github.com/objectify/objectify/wiki/Entities#load

  /**
   * The unique ID of the {@link User} who committed this transgression.
   */
  public Long offenderId;

  /**
   * The unique ID of the {@link User} who reported this transgression, if any.
   */
  public Long victimId;

  /**
   * The unique ID of the moderator who enacted this {@link Ban}.
   */
  public Long modId;

  /**
   * The reason the {@linkplain #offenderId offender}'s privileges were revoked.
   */
  public String reason;

  /**
   * The {@link Date} on which the {@linkplain #offenderId offender}'s privileges were revoked.
   */
  public Date bannedOn;

  /**
   * The {@link Date} on which the {@linkplain #offenderId offender}'s privileges will be restored.
   */
  public Date expires;

  public Ban() {
    /* Needed by Objectify */
  }

  public Ban(final User offender, final User victim, final User mod, final String reason,
      final Date banExpiration) {
    this.offenderId = offender.id;
    this.victimId = victim.id;
    this.modId = mod.id;
    this.reason = reason;
    this.bannedOn = new Date();
    this.expires = banExpiration;
  }
}
