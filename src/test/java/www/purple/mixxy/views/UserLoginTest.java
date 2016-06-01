package www.purple.mixxy.views;

import org.junit.Test;

import ninja.NinjaFluentLeniumTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserLoginTest extends NinjaFluentLeniumTest {

  @Test
  public void testClickGoogle() {
    goTo(getServerAddress() + "/");

    click("#login");
    click("#Google");

    String url = this.url();
    assertThat(url, containsString("login"));
    assertThat(url, containsString("google"));
    // assertThat(title(), not(containsString("error")));
  }

  @Test
  public void testClickFacebook() {
    goTo(getServerAddress() + "/");

    click("#login");
    click("#Facebook");

    String url = this.url();
    assertThat(url, containsString("login"));
    assertThat(url, containsString("facebook"));
    // assertThat(title(), not(containsString("error")));
  }
}
