package www.purple.mixxy.conf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.inject.Injector;

import ninja.utils.NinjaProperties;
import ninja.NinjaTest;

public class GcsTest extends NinjaTest {
  private GcsService gcs;

  private NinjaProperties properties;

  @Before
  public void injectApiKeys() {
    Injector injector = getInjector();
    gcs = injector.getInstance(GcsService.class);
    properties = injector.getInstance(NinjaProperties.class);
  }

  @Test
  public void testGcsInjected() {
    assertNotNull(gcs);
  }
}
