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
import com.googlecode.objectify.Ref;

import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.ComicDto;
import www.purple.mixxy.models.ComicsDto;
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
		return objectify.get().load().type(Comic.class).filter("author", author).list();
	}

	public List<Comic> getComics(String userName) {
		return objectify.get().load().type(Comic.class).filter("username", userName).list();
	}

	public Comic getComic(long id) {
		return objectify.get().load().type(Comic.class).id(id).now();
	}

	public Comic getComic(User user, String slug) {
		return null;
	}

	public List<Comic> getRemixes(Comic comic) {
		return Collections.EMPTY_LIST;
	}

	public List<Comic> getRemixes(long id) {
		return Collections.EMPTY_LIST;
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

		if (user == null) {
			return false;
		}

		Comic comic = new Comic(user, comicDto.title, comicDto.description, comicDto.tags);

//		comic.author = Ref.create(user);

		// lowest index is the root Parent comic (index 0 is the first comic
		// iteration)
		comic.ancestorComics.add(Ref.create(comic));
		objectify.get().save().entity(comic);

		return true;

	}

	public void saveComic(Long id, Comic comic) {
		// TODO
	}

	public Comic branchComic(Long id) {
		// TODO
		return null;
	}

	public void likeComic(Long id) {
		// TODO
	}

}
