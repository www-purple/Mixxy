package www.purple.mixxy.conf;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.inject.Inject;

import ninja.NinjaRunner;
import ninja.NinjaTest;
import ninja.utils.NinjaProperties;

@RunWith(NinjaRunner.class)
public class PropertiesTest extends NinjaTest {

  @Inject
  private NinjaProperties properties;

  @Test
  public void testPropertiesInjected() {
    assertNotNull(properties);
  }

  @Test
  public void testUsingTestMode() {
    assertTrue(properties.isTest());
  }

  @Test
  public void testUsingUtf8() {
    assertTrue(properties.getBoolean("utf-8"));
  }

  @Test
  public void testPackage() {
    assertEquals("www.purple.mixxy", properties.get("application.modules.package"));
  }
}
