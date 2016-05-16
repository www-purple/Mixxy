package www.purple.mixxy.conf;

import java.util.Arrays;

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

        User bob = new User("BobTheBuilder", "Bob", "Smith", "male", "bob@gmail.com", "url", "en", "123", "google");
        bob.id = (long) -32;

        Comic bobComic = new Comic(null, bob, "cool title", "interesting description", "boring junk", Arrays.asList("18+", "sci-fi", "sexy"));
        bobComic.id = (long) -1;

        Comic bobComic2 = new Comic(bobComic, bob, "cool title222", "interesting description222", "nada", Arrays.asList("parody", "derp"));
        bobComic2.id = (long) -2;

        bobComic2.ancestorComicId.add(bobComic.id);
        if (user == null) {
            // Create a new user and save it
            if (ofy.load().entity(bob).now() == null) {
              ofy.save().entity(bob).now();
            }
        }

        // Comic ancestry working
        if (ofy.load().type(Comic.class).first().now() == null) {
            ofy.save().entities(bobComic, bobComic2).now();
        }

    }

}
