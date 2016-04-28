package www.purple.mixxy.conf;

import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import www.purple.mixxy.models.Ban;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.Flag;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.Subscription;
import www.purple.mixxy.models.User;

public class ObjectifyProvider implements Provider<Objectify> {

    @Override
    public Objectify get() {
        return ObjectifyService.ofy();
    }
    
    static {

        ObjectifyService.register(User.class);
        ObjectifyService.register(Comic.class);
        ObjectifyService.register(Ban.class);
        ObjectifyService.register(Flag.class);
        ObjectifyService.register(Like.class);
        ObjectifyService.register(Subscription.class);

        setup();
    }


    public static void setup() {
        Objectify ofy = ObjectifyService.ofy();
        User user = ofy.load().type(User.class).first().now();

        if (user == null) {

            // Create a new user and save it
            User bob = new User("BobTheBuilder", "Bob", "Smith", "male", "bob@gmail.com", "url", "en", "123", "google");
            ofy.save().entity(bob).now();

            // Comic ancestry working
            Comic bobComic = new Comic(null, bob, "cool title", "interesting description", null);
            ofy.save().entities(bobComic).now();
            bobComic.ancestorComicId.add(bobComic.id);
            ofy.save().entities(bobComic).now();
            
            Comic bobComic2 = new Comic(bobComic, bob, "cool title222", "interesting description222", null);
            ofy.save().entities(bobComic2).now();
            bobComic2.ancestorComicId.add(bobComic2.id);
            ofy.save().entities(bobComic2).now();
            
        }

    }

}
