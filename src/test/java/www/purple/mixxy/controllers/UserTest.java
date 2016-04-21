package www.purple.mixxy.controllers;

import com.googlecode.objectify.Objectify;

import org.junit.Test;
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class UserTest extends NinjaAppengineBackendTest {
    
    private static final String USER_EMAIL = "bob@gmail.com";
    private static final String USER_FIRSTNAME = "Bob";
    private static final String USER_LASTNAME = "Smith";
    private static final String USER_USERNAME= "BobTheBuilder";
    
    private ObjectifyProvider provider;
    
    @Before
    public void before() {
      provider = new ObjectifyProvider();
    }

    @Test
    public void testCreateAndRetrieveUser() {

        Objectify ofy = provider.get();

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = ofy.load().type(User.class).filter("username", USER_USERNAME).first().now();
        
        //	Check if the User already exists in the Datastore
        //	if not, create that user, otherwise use the User instance
        if(bob.username != USER_USERNAME){
        	// Create a new user and save it
            User user = new User(USER_USERNAME, USER_FIRSTNAME, USER_LASTNAME, "male", USER_EMAIL, "url", "en", "123", "google");
            ofy.save().entity(user).now();
        }
        
        // Test
        assertNotNull(bob);
        assertEquals(USER_FIRSTNAME, bob.firstname);
        assertEquals(USER_LASTNAME, bob.lastname);
    }

}
