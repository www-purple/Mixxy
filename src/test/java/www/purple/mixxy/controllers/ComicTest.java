package www.purple.mixxy.controllers;

import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.Ref;

import org.junit.Test;
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class ComicTest extends NinjaAppengineBackendTest {

	@Test
	public void testCreateComic() {

		ObjectifyProvider objectifyProvider = new ObjectifyProvider();
		Objectify ofy = objectifyProvider.get();

		// Create a new user and save it
		User yetAnotherBob = new User("BobTheBuilderFriend2", "Bob", "King", "clonedbob@gmail.com", "", "", "", "", "");
		ofy.save().entity(yetAnotherBob).now();

		// Create a new comic
		Comic comic = new Comic(null, yetAnotherBob, "cool title", "interesting description", null);

		//comic.author = Ref.create(yetAnotherBob);
		ofy.save().entity(comic).now();

		// Test that the post has been created
		assertNotNull(ofy.load().type(Comic.class).first().now());
		
		// Received comic using multiple filters with UserName and SluggedTitle
		User user = ofy.load().type(User.class).filter("username", yetAnotherBob.username).first().now();
		Comic recievedComic = ofy.load().type(Comic.class).filter("authorId", user.id).filter("sluggedTitle", comic.sluggedTitle).first().now();
		assertNotNull(recievedComic);
		assertEquals(recievedComic, comic);
		
		// Check if saved work done on comic
		recievedComic.description = "sickness";
		ofy.save().entity(recievedComic).now();
		Comic newComic = ofy.load().type(Comic.class).id(recievedComic.id).now();

		assertEquals("sickness", newComic.description);
		
		// Retrieve all posts created by Bob
		List<Comic> bobPosts = ofy.load().type(Comic.class)
				.filter("authorId", yetAnotherBob.id).list();
		
		
		// Basic Comic Tests
		assertEquals(1, bobPosts.size());
		Comic firstPost = bobPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(yetAnotherBob.id, firstPost.authorId);
		assertEquals("cool title", firstPost.title);
		assertEquals("sickness", firstPost.description);
		assertNotNull(firstPost.createdAt);
	}

}
