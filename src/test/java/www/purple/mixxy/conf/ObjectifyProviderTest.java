package www.purple.mixxy.conf;

import ninja.NinjaAppengineBackendTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * NinjaTestRunner is not used here, this test breaks if so
 * 
 * @author jesse
 *
 */
public class ObjectifyProviderTest extends NinjaAppengineBackendTest {

  private ObjectifyProvider provider;

  @Before
  public void setup() {
    this.provider = this.getInjector().getInstance(ObjectifyProvider.class);
  }

  @Test
  public void testObjectifyProviderInjected() {
    assertNotNull(provider);
  }

  @Test
  public void testProviderReturnsObjectifyInstance() {
    assertNotNull(provider.get());
  }
}
