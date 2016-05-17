package www.purple.mixxy.conf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.inject.Inject;
import com.google.inject.Injector;

import ninja.utils.NinjaProperties;
import ninja.NinjaRunner;
import ninja.NinjaTest;

@RunWith(NinjaRunner.class)
public class GcsTest extends NinjaTest {
  @Inject
  private GcsService gcs;

  @Inject
  private NinjaProperties properties;

  @Test
  public void testGcsInjected() {
    assertNotNull(gcs);
  }
}
