package www.purple.mixxy.controllers;

import org.junit.Test;
import ninja.NinjaFluentLeniumTest;

import static org.junit.Assert.*;

public class NavigationTest extends NinjaFluentLeniumTest {

  @Test
  public void testIndexWithTrailingSlashIsOk() {
    String url = getServerAddress();

    goTo(url + "/");

    assertEquals(url + "/", url());
  }

  @Test
  public void testIndexWithoutTrailingSlashIsOk() {
    String url = getServerAddress();

    goTo(url);

    assertEquals(url, url());
  }
}
