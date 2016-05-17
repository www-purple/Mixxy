package www.purple.mixxy.dao;

import java.util.Collections;
import java.util.List;

/**
 * Created by Brian_Sabz on 4/7/16.
 * 
 * @author Brian_Sabz
 */
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ComicDto;
import www.purple.mixxy.models.ComicsDto;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.User;

public class ComicDao {

	@Inject
	private Provider<Objectify> objectify;

	public ComicsDto getAllComics() {

		ComicsDto comicsDto = new ComicsDto();
		comicsDto.comics = objectify.get().load().type(Comic.class).list();

		return comicsDto;

	}

	public List<Comic> getComics(User author) {
		return objectify.get().load().type(Comic.class).filter("authorIds", author.id).list();
	}

	public List<Comic> getComics(String username) {

		User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		if (user != null) {
		  // If the requested user exists...
		  return objectify.get().load().type(Comic.class).filter("authorId", user.id).list();
		}
		else {
		  return null;
		}
	}
	
	public List<Like> getLikes(String username, String slug){
		
		Comic comic = getComic(username, slug);
		
		return objectify.get().load().type(Like.class).filter("comicId", comic.id).list();
		
	}

	public Comic getComic(long id) {
		return objectify.get().load().type(Comic.class).id(id).now();
	}

	public Comic getComic(String username, String slug) {
	    if (username == null || slug == null) return null;

	    User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		return objectify.get().load().type(Comic.class).filter("authorId", user.id).filter("sluggedTitle", slug).first()
				.now();
	}

	// return comics by user of a certain series
	public List<Comic> getSeries(String username, String series) {
		if (username == null || series == null) return null;

		User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		return objectify.get().load().type(Comic.class).filter("authorId", user.id).filter("series", series).list();
	}

	public Comic getComic(User user, String slug) {
	    if (user == null || slug == null) return null;

	    return getComic(user.username, slug);
	}

	public Comic getRemix(long id) {
		return objectify.get().load().type(Comic.class).filter("ancestorComicId", id).first().now();
	}

	public List<Comic> getRemixes(String username, String slug) {
		
		Comic comic = getComic(username, slug);
		
		return objectify.get().load().type(Comic.class).filter("ancestorComicId", comic.id).list();
	}

	public List<Comic> getRemixes(long id) {
		return objectify.get().load().type(Comic.class).filter("ancestorComicId", id).list();
	}

	public List<Comic> getAncestors(Comic comic) {
		return Collections.EMPTY_LIST;
	}

	public List<Comic> getAncestors(long id) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @param username
	 *            Name of the user who is posting this Comic
	 * @param comicDto
	 *            ComicDto that contains comic's content
	 * @return false if user cannot be found in database.
	 */
	public boolean newComic(String username, ComicDto comicDto) {

		User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		if (user == null || comicDto.title == null || comicDto.tags == null) {
			return false;
		}

		Comic comic = new Comic(null, user, comicDto.title, comicDto.description, comicDto.series, comicDto.tags);

		// comic.author = Ref.create(user);

		// lowest index is the root Parent comic (index 0 is the first comic
		// iteration)
		// comic.ancestorComics.add(Ref.create(comic));
		objectify.get().save().entity(comic).now();
		comic.ancestorComicId.add(comic.id);
		comic.sluggedTitle = comic.sluggedTitle + "-" + Long.toString(comic.id);
		objectify.get().save().entity(comic).now();

		return true;

	}

	public boolean saveComic(String username, String slug) {

		Comic comic = getComic(username, slug);
		
		if (comic == null) {
			return false;
		}

		objectify.get().save().entity(comic).now();

		return true;
	}

	public boolean deleteComic(String username, String slug) {
		
		Comic comic = getComic(username, slug);

		if (comic == null) {
			return false;
		}

		objectify.get().delete().entity(comic);

		return true;
	}

	public Comic branchComic(String username, Long id) {

		User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		Comic parentComic = objectify.get().load().type(Comic.class).id(id).now();

		Comic remixedComic = new Comic(parentComic, user, parentComic.title, parentComic.description, parentComic.series, parentComic.tags);

		objectify.get().save().entity(remixedComic).now();
		remixedComic.ancestorComicId.add(remixedComic.id);
		objectify.get().save().entity(remixedComic).now();

		return remixedComic;
	}

	public boolean likeComic(String username, Long comicId) {

		User user = objectify.get().load().type(User.class).filter("username", username).first().now();

		if (user == null) {
			return false;
		}

		Comic likedComic = objectify.get().load().type(Comic.class).id(comicId).now();

		Like like = new Like(likedComic, user);

		objectify.get().save().entity(like).now();

		return true;
	}

}
