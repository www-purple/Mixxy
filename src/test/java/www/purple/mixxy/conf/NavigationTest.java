package www.purple.mixxy.conf;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.Singleton;

import ninja.NinjaFluentLeniumTest;

@Singleton
public class NavigationTest extends NinjaFluentLeniumTest {

  @Test
  public void indexWithTrailingSlashIsOk() {
    String url = getServerAddress();

    goTo(url + "/");

    assertEquals(url + "/", url());
  }

  @Test
  public void indexWithoutTrailingSlashIsOk() {
    String url = getServerAddress();

    goTo(url);

    assertEquals(url, url());
  }
}
