package www.purple.mixxy.conf;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import ninja.NinjaAppengineBackendTest;
import ninja.NinjaTest;
import www.purple.mixxy.helpers.ApiKeys;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectifyProviderTest extends NinjaAppengineBackendTest {

  private ObjectifyProvider provider;

  @Before
  public void injectObjectifyProvider() {
    Injector injector = this.getInjector();
    provider = injector.getInstance(ObjectifyProvider.class);
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
