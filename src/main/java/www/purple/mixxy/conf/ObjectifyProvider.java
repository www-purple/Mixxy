package www.purple.mixxy.conf;

import java.util.Arrays;

import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import www.purple.mixxy.models.Article;
import www.purple.mixxy.models.Ban;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.Flag;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.Subscription;
import www.purple.mixxy.models.User;

public class ObjectifyProvider implements Provider<Objectify> {

    public static final String POST1_TITLE = "Hello to the blog example!";
    public static final String POST1_CONTENT
            = "<p>Hi and welcome to the demo of Ninja!</p> "
            + "<p>This example shows how you can use Ninja in the wild. Some things you can learn:</p>"
            + "<ul>"
            + "<li>How to use the templating system (header, footer)</li>"
            + "<li>How to test your application with ease.</li>"
            + "<li>Setting up authentication (login / logout)</li>"
            + "<li>Internationalization (i18n)</li>"
            + "<li>Static assets / using webjars</li>"
            + "<li>Persisting data</li>"
            + "<li>Beautiful <a href=\"/article/3\">html routes</a> for your application</li>"
            + "<li>How to design your restful Api (<a href=\"/api/bob@gmail.com/articles.json\">Json</a> and <a href=\"/api/bob@gmail.com/articles.xml\">Xml</a>)</li>"
            + "<li>... and much much more.</li>"
            + "</ul>"
            + "<p>We are always happy to see you on our mailing list! "
            + "Check out <a href=\"http://www.ninjaframework.org\">our website for more</a>.</p>";
    private static final String LIPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit sed nisl sed lorem commodo elementum in a leo. Aliquam erat volutpat. Nulla libero odio, consectetur eget rutrum ac, varius vitae orci. Suspendisse facilisis tempus elit, facilisis ultricies massa condimentum in. Aenean id felis libero. Quisque nisl eros, accumsan eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula.";
    
    @Override
    public Objectify get() {
        return ObjectifyService.ofy();
    }
    
    static {

        ObjectifyService.register(User.class);
        ObjectifyService.register(Article.class);
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

        Comic bobComic = new Comic(null, bob, "cool title", "interesting description", Arrays.asList("18+", "sci-fi", "sexy"));
        bobComic.id = (long) -1;

        Comic bobComic2 = new Comic(bobComic, bob, "cool title222", "interesting description222", Arrays.asList("parody", "derp"));
        bobComic2.id = (long) -2;

        bobComic2.ancestorComicId.add(bobComic.id);
        if (user == null) {

            // Create a new user and save it
            if (ofy.load().entity(bob).now() == null) {
              ofy.save().entity(bob).now();
            }

            // Create a new post
            Article bobPost3 = new Article(bob, "My third post", LIPSUM);
            ofy.save().entity(bobPost3).now();

            // Create a new post
            Article bobPost2 = new Article(bob, "My second post", LIPSUM);
            ofy.save().entity(bobPost2).now();

            // Create a new post
            Article bobPost1 = new Article(bob, POST1_TITLE, POST1_CONTENT);
            ofy.save().entity(bobPost1).now();
        }

        // Comic ancestry working
        if (ofy.load().type(Comic.class).first().now() == null) {
            ofy.save().entities(bobComic, bobComic2).now();
        }

    }

}
