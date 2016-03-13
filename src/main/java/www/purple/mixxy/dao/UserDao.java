package www.purple.mixxy.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.User;

public class UserDao {

    @Inject
    Provider<Objectify> objectify;
    
    public boolean isUserAndPasswordValid(String username, String password) {
        
        if (username != null && password != null) {

            User user = objectify.get().load().type(User.class)
                    .filter("username", username).first().now();
            
            if (user != null) {
                
                if (user.password.equals(password)) {

                    return true;
                }
                
            }

        }
        
        return false;
 
    }

}
