package www.purple.mixxy.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.User;

public class UserDao {

    @Inject
    private Provider<Objectify> objectify;
    
    public boolean isUserValid(String username) {
        
        if (username != null) {

            User user = objectify.get().load().type(User.class)
                    .filter("username", username).first().now();
            
            if (user != null) {
                return true;              
            }
        }
        
        return false;
 
    }

}
