package www.purple.mixxy.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import www.purple.mixxy.helpers.ApiKeys;

@Singleton
public class ApiKeyLoadingTest  extends NinjaAppengineBackendTest {
  
  @Inject
  private ApiKeys apiKeys;
  
  @Test
  public void testApiKeysLoaded() {
    assertNotNull(apiKeys);
  }
  
  @Test
  public void testGoogleIdLoaded() {
    assertNotNull(apiKeys.getGoogleId());
    assertFalse(apiKeys.getGoogleId().isEmpty());
  }
  
  @Test
  public void testGoogleSecretLoaded() {
    assertNotNull(apiKeys.getGoogleSecret());
    assertFalse(apiKeys.getGoogleSecret().isEmpty());
  }
  
  @Test
  public void testFacebookIdLoaded() {
    assertNotNull(apiKeys.getFacebookId());
    assertFalse(apiKeys.getFacebookId().isEmpty());
  }
  
  @Test
  public void testFacebookSecretLoaded() {
    assertNotNull(apiKeys.getFacebookSecret());
    assertFalse(apiKeys.getFacebookSecret().isEmpty());
  }
}
