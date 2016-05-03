package www.purple.mixxy.models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Represents the act of one {@link User} following another.
 *
 * @author Brian Sabzjadid
 */
@Entity
public class Subscription {
  /**
   * Unique identifier for this {@link Subscription}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  /**
   * The unique ID of the {@link User} doing the following. Must not equal {@link #publisherId}.
   */
  public Long subscriberId;

  /**
   * The unique ID of the {@link User} who was followed. Must not equal {@link #subscriberId}.
   */
  public Long publisherId;

  /**
   * The date on which this {@link Subscription} was made.
   */
  public Date createdAt;

  public Subscription() {
    /* Needed by Objectify */
  }

  public Subscription(final User subscriber, final User publisher) {
    this.subscriberId = subscriber.id;
    this.publisherId = publisher.id;
    this.createdAt = new Date();
  }
}
