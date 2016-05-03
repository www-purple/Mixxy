package www.purple.mixxy.models;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.github.slugify.Slugify;
import com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Represents a comic or other work of art in the application. This is why people will want to use
 * Mixxy, so you'd better make this good!
 *
 * @author Brian Sabzjadid
 */
@Entity
@Index
public class Comic {

  /**
   * Unique identifier for this {@link Comic}. Do not assume any particular pattern.
   */
  @Id
  public Long id;

  // can refer to these Entities using the already fetched Comic, this solve
  // performance problems
  // See: https://github.com/objectify/objectify/wiki/Entities#load
  // @Load
  // public Ref<User> author;

  /**
   * The unique identifier of the {@link User} who authored or remixed this {@link Comic}. Don't
   * assume this will never change; it's possible we may later on implement some kind of ownership
   * transfer, like with GitHub repos.
   */
  public Long authorId;

  /**
   * List of {@link Comic} IDs that this one is derived from, with the first being the most recent.
   * An empty list means this is a root-level {@link Comic}.
   */
  public List<Long> ancestorComicId;

  /**
   * The {@link User}-designated title of this {@link Comic}. Multiple {@link Comic}s, whether from
   * the same {@link User} or not, may have the same title.
   */
  public String title;

  /**
   * The {@linkplain Slugify slug-cased} title of this {@link Comic}, a transformed version of
   * {@link #title}. Used for identification via URL. To resolve ambiguity, append {@link #id} to
   * the end of this. Should never change, even if {@link #title} does.
   */
  public String sluggedTitle;

  /**
   * The {@link User}-designated description of this {@link Comic}.
   */
  @Unindex
  public String description;

  /**
   * Key words the {@link User} has chosen to identify this {@link Comic} with.
   */
  public List<String> tags;

  /**
   * The {@link Date} on which this {@link Comic} was created or remixed. Should never change.
   */
  public Date createdAt;

  /**
   * The {@link Date} on which this {@link Comic} was last updated, either in its content or in its
   * metadata ({@link #tags}, {@link #description}, etc.).
   */
  public Date updatedAt;

  public Comic() {
    /* needed by Objectify */
  }

  public Comic(final Comic ancestorComic, final User author, final String title,
      final String description,
      final List<String> tags) {

    if (ancestorComic == null) {
      this.ancestorComicId = Lists.newArrayList();
    }
    else {
      this.ancestorComicId = ancestorComic.ancestorComicId;
    }
    this.authorId = author.id;

    Slugify slg;
    try {
      slg = new Slugify();
      this.sluggedTitle = slg.slugify(title);
    }
    catch (IOException e) {
      // NOTE: Unlikely to occur; if it does, there's likely a bigger problem.
      // Slugify reads from a .properties file that is included in its own resources,
      // and you can't override it (so no chance of misuse).
      e.printStackTrace();
    }

    this.title = title;
    this.description = description;
    this.tags = tags;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

}
