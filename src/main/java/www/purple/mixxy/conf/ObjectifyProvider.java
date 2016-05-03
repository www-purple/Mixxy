package www.purple.mixxy.conf;

import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import www.purple.mixxy.models.Ban;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.Flag;
import www.purple.mixxy.models.Like;
import www.purple.mixxy.models.Subscription;
import www.purple.mixxy.models.User;

/**
 * Provides an {@link Objectify} instance for {@linkplain Inject injection} into any class,
 * especially those used with Ninja. {@link ObjectifyService#ofy()} does not recommend actually
 * holding on to an {@link Objectify} instance, hence the injection of a {@link Provider} instead of
 * an {@link Objectify} instance directly.
 */
public class ObjectifyProvider implements Provider<Objectify> {

  private static final long BOB_ID = -32;
  private static final long BOB_COMIC_ID = -1;
  private static final long BOB_COMIC_ID2 = -2;

  static {
    ObjectifyService.register(User.class);
    ObjectifyService.register(Comic.class);
    ObjectifyService.register(Ban.class);
    ObjectifyService.register(Flag.class);
    ObjectifyService.register(Like.class);
    ObjectifyService.register(Subscription.class);

    setup();
  }

  @Override
  public Objectify get() {
    return ObjectifyService.ofy();
  }

  /**
   * Initializes dummy data, for use in development and testing. <b>Do not call in production
   * mode.</b>
   */
  public static void setup() {
    Objectify ofy = ObjectifyService.ofy();
    User user = ofy.load().type(User.class).first().now();

    if (user == null) {
      // If we haven't yet registered our dummy data (even in a previous run)...

      // Create a user
      User bob = new User("BobTheBuilder", "Bob", "Smith", "male", "bob@gmail.com", "url", "en",
          "123", "google");
      bob.id = BOB_ID;

      // Give him a comic and a remix of it
      Comic bobComic = new Comic(null, bob, "cool title", "interesting description",
          Arrays.asList("18+", "sci-fi", "sexy"));
      bobComic.id = BOB_COMIC_ID;

      Comic bobComic2 = new Comic(bobComic, bob, "cool title222", "interesting description222",
          Arrays.asList("parody", "derp"));
      bobComic2.id = BOB_COMIC_ID2;

      bobComic2.ancestorComicId.add(bobComic.id);
      // TODO: Should we allow a user to remix their own comic?

      if (ofy.load().entity(bob).now() == null) {
        // If Bob doesn't already exist...
        ofy.save().entity(bob).now();
      }

      if (ofy.load().type(Comic.class).first().now() == null) {
        // If we don't have any dummy comics registered yet...
        ofy.save().entities(bobComic, bobComic2).now();
      }
    }
  }
}
