package www.purple.mixxy.conf;

import com.google.inject.Inject;

import ninja.NinjaRunner;
import ninja.NinjaTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import www.purple.mixxy.helpers.ApiKeys;

@RunWith(NinjaRunner.class)
public class ApiKeyLoadingTest extends NinjaTest {

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
