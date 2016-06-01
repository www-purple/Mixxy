package www.purple.mixxy.models;

/**
 * A report of community standards violation, pending moderator intervention.
 *
 * @see Ban
 *
 * @author Brian Sabz
 */
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Flag {
  /**
   * Unique identifier for this {@link Flag}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load

  /**
   * The unique identifier of the {@link User} who reported this violation. Should never change.
   */
  public Long flaggerId;

  /**
   * The unique identifier of the {@link User} who was flagged. Should never change.
   *
   * @TODO: {@link Comic}s, {@link User}s, <i>and</i> comments should be reportable. Maybe we can
   *        support them all with a {@code TYPE} {@code enum} or something?
   */
  public Long flaggedUserId;

  /**
   * The {@link User}-provided reason this is considered a violation.
   */
  public String reason;

  /**
   * The {@link Date} on which this violation was reported.  Should never change.
   */
  public Date createdAt;

  public Flag() {
    /* Needed by Objectify */
  }

  public Flag(final User flagger, final User flaggedUser, final String reason) {
    /* Needed by Objectify */
    this.flaggerId = flagger.id;
    this.flaggedUserId = flaggedUser.id;
    this.reason = reason;
    this.createdAt = new Date();
  }
}
