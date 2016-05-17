package www.purple.mixxy.conf;

import com.google.inject.Inject;

import ninja.NinjaAppengineBackendTest;
import ninja.NinjaRunner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(NinjaRunner.class)
public class ObjectifyProviderTest extends NinjaAppengineBackendTest {

  @Inject
  private ObjectifyProvider provider;

  @Test
  public void testObjectifyProviderInjected() {
    assertNotNull(provider);
  }

  @Test
  public void testProviderReturnsObjectifyInstance() {
    assertNotNull(provider.get());
  }
}
