package www.purple.mixxy.controllers;

import com.googlecode.objectify.Objectify;
import org.junit.Test;
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class UserTest extends NinjaAppengineBackendTest {
    
    private static final String USER_EMAIL = "bob@gmail.com";
    private static final String USER_FIRSTNAME = "Bob";
    private static final String USER_LASTNAME = "Smith";
    private static final String USER_USERNAME= "BobTheBuilder";

    
    @Test
    public void testCreateAndRetrieveUser() {
        
        ObjectifyProvider objectifyProvider = new ObjectifyProvider();
        Objectify ofy = objectifyProvider.get();
        
        
        // Create a new user and save it
        User user = new User(USER_USERNAME, USER_FIRSTNAME, USER_LASTNAME, USER_EMAIL, "url", "en", "google", "123");
        ofy.save().entity(user).now();
        

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = ofy.load().type(User.class).filter("username", USER_USERNAME).first().now();
        
        // Test
        assertNotNull(bob);
        assertEquals(USER_FIRSTNAME, bob.firstname);
        assertEquals(USER_LASTNAME, bob.lastname);
    }

}
