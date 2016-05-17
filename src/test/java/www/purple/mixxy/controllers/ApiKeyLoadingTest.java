package www.purple.mixxy.controllers;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import ninja.NinjaTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import www.purple.mixxy.helpers.ApiKeys;

public class ApiKeyLoadingTest extends NinjaTest {

  private ApiKeys apiKeys;
  
  @Before
  public void injectApiKeys() {
    Injector injector = getInjector();
    apiKeys = injector.getInstance(ApiKeys.class);
  }

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

  @Test
  public void testDeviantartIdLoaded() {
    assertNotNull(apiKeys.getDeviantartId());
    assertFalse(apiKeys.getDeviantartId().isEmpty());
  }

  @Test
  public void testDeviantartKeyLoaded() {
    assertNotNull(apiKeys.getDeviantartKey());
    assertFalse(apiKeys.getDeviantartKey().isEmpty());
  }
}
