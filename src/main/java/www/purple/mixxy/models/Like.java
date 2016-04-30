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
public class Like {
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load
  public Long comicId;
  public Long userId;

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
