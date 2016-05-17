package www.purple.mixxy.conf;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.inject.Injector;

import ninja.NinjaTest;
import ninja.utils.NinjaProperties;

public class PropertiesTest extends NinjaTest {

  private NinjaProperties properties;

  @Before
  public void injectProperties() {
    Injector injector = this.getInjector();
    properties = injector.getInstance(NinjaProperties.class);
  }

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
