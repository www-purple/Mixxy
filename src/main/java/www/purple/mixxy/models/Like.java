package www.purple.mixxy.models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Brian_Sabz on 4/5/16.
 *
 * @author Brian Sabzjadid
 */
@Entity
public class Like {
  /**
   * The unique identifier of this {@link Like}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load

  /**
   * The {@link Comic} that was {@link Like}d.
   */
  public Long comicId;

  /**
   * The {@link User} who {@link Like}d {@linkplain #comicId this comic}.
   */
  public Long userId;

  /**
   * The {@link Date} on which this {@linkplain #comicId comic} was {@link Like}d.
   */
  public Date createdAt;

  public Like() {
    /* Needed by Objectify */
  }

  public Like(final Comic comic, final User user) {
    this.comicId = comic.id;
    this.userId = user.id;
    this.createdAt = new Date();
  }
}
