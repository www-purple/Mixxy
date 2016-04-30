package www.purple.mixxy.models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Brian_Sabz on 4/5/16.
 *
 * @author Brian_Sabz
 */
@Entity
public class Subscription {
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load
  public Long subscriberId;
  public Long publisherId;

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
