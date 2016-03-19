package www.purple.mixxy.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.models.User;

import org.junit.Test;

import com.googlecode.objectify.Objectify;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class UserTest extends NinjaAppengineBackendTest {
    
    private static final String USER_EMAIL = "bob@gmail.com";
    private static final String USER_NAME = "Bob";
    
    @Test
    public void testCreateAndRetrieveUser() {
        
        ObjectifyProvider objectifyProvider = new ObjectifyProvider();
        Objectify ofy = objectifyProvider.get();
        
        
        // Create a new user and save it
        User user = new User(USER_EMAIL, "secret", USER_NAME);
        ofy.save().entity(user).now();
        

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = ofy.load().type(User.class).filter("username", USER_EMAIL).first().now();

        // Test
        assertNotNull(bob);
        assertEquals(USER_NAME, bob.fullname);
    }

}
