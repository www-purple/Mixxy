package www.purple.mixxy.controllers;

import com.googlecode.objectify.Objectify;
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
        User yetAnotherBob = new User("BobTheBuilderFriend2", "Bob", "King", "clonedbob@gmail.com");
        ofy.save().entity(yetAnotherBob).now();
        
        //	Create a new comic
        Comic comic = new Comic("cool title", "interesting description", null);
        ofy.save().entity(comic).now();
        
        // Test that the post has been created
        assertNotNull(ofy.load().type(Comic.class).first().now());
        
        // Retrieve all posts created by Bob
        List<Comic> bobPosts = ofy.load().type(Comic.class)
                .filter("author",  yetAnotherBob).list();

        
        // Tests
        assertEquals(1, bobPosts.size());
        Comic firstPost = bobPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(yetAnotherBob, firstPost.author);
        assertEquals("cool title", firstPost.title);
        assertEquals("interesting description", firstPost.description);
        assertNotNull(firstPost.createdAt);
    }
    
}
