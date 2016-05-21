package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/5/16.
 * 
 * @author Brian_Sabz
 */
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Flag {
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load

  public Long flaggerId;
  public Long flaggedUserId;

  public String reason;

  public Date createdAt;

  public Flag() {
    /* Needed by Objectify */}

  public Flag(final User flagger, final User flaggedUser, final String reason) {
    /* Needed by Objectify */
    this.flaggerId = flagger.id;
    this.flaggedUserId = flaggedUser.id;
    this.reason = reason;
    this.createdAt = new Date();
  }
}
