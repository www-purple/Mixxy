package www.purple.mixxy.views;

import org.junit.Test;

import ninja.NinjaFluentLeniumTest;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

public class LayoutTest extends NinjaFluentLeniumTest {

  @Test
  public void testAuthor() {
    goTo(getServerAddress() + "/");

    FluentList<FluentWebElement> author = find("meta", withName("author"));

    assertFalse(author.isEmpty());
    assertEquals("www.purple", author.getAttribute("content"));
  }
}
