package www.purple.mixxy.controllers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import ninja.NinjaAppengineBackendTest;
import ninja.Results;

@RunWith(Parameterized.class)
public class GeneralGetUrlTest extends NinjaAppengineBackendTest {

  @Parameters(name = "{0} returns {1}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {

        // User page
        { "BobTheBuilder", 200 },
        { "BobTheBuilder/", 200 },
        { "users/BobTheBuilder", 200 },
        { "users/BobTheBuilder/", 200 },

        { "adsfdasfasdfas", 404 },
        { "adsfdasfasdfas/", 404 },
        { "users/adsfdasfasdfas", 404 },
        { "users/adsfdasfasdfas/", 404 },

        // Gallery page
        { "BobTheBuilder/works", 501 },
        { "BobTheBuilder/works/", 501 },
        { "users/BobTheBuilder/works", 501 },
        { "users/BobTheBuilder/works/", 501 },

        { "ggggggtgtgtgtgt/works", 404 },
        { "ggggggtgtgtgtgt/works/", 404 },
        { "users/ggggggtgtgtgtgt/works", 404 },
        { "users/ggggggtgtgtgtgt/works/", 404 },

        // Subscriber list
        { "BobTheBuilder/subscribers", 501 },
        { "BobTheBuilder/subscribers/", 501 },
        { "users/BobTheBuilder/subscribers/", 501 },
        { "users/BobTheBuilder/subscribers/", 501 },

        { "ggggggtgtgtgtgt/subscribers", 404 },
        { "ggggggtgtgtgtgt/subscribers/", 404 },
        { "users/ggggggtgtgtgtgt/subscribers/", 404 },
        { "users/ggggggtgtgtgtgt/subscribers/", 404 },

        // Subscription list
        { "BobTheBuilder/subscribed", 501 },
        { "BobTheBuilder/subscribed/", 501 },
        { "users/BobTheBuilder/subscribed/", 501 },
        { "users/BobTheBuilder/subscribed/", 501 },

        { "ggggggtgtgtgtgt/subscribed", 404 },
        { "ggggggtgtgtgtgt/subscribed/", 404 },
        { "users/ggggggtgtgtgtgt/subscribed/", 404 },
        { "users/ggggggtgtgtgtgt/subscribed/", 404 },

        // Series list
        /*
         * { "BobTheBuilder/series", 200 }, { "BobTheBuilder/series/", 200 }, {
         * "users/BobTheBuilder/series/", 200 }, { "users/BobTheBuilder/series/", 200 },
         * 
         * { "ggggggtgtgtgtgt/series", 404 }, { "ggggggtgtgtgtgt/series/", 404 }, {
         * "users/ggggggtgtgtgtgt/series/", 404 }, { "users/ggggggtgtgtgtgt/series/", 404 },
         */

        // Comic page
        { "BobTheBuilder/cool-title", 200 },
        { "users/BobTheBuilder/cool-title", 200 },
        { "BobTheBuilder/works/cool-title", 200 },
        { "users/BobTheBuilder/works/cool-title", 200 },
        { "BobTheBuilder/cool-title/", 200 },
        { "users/BobTheBuilder/cool-title/", 200 },
        { "BobTheBuilder/works/cool-title/", 200 },
        { "users/BobTheBuilder/works/cool-title/", 200 },

        { "BobTheBuilder/fgsfdssdavcrsacasfrfsd", 404 },
        { "users/BobTheBuilder/fgsfdssdavcrsacasfrfsd", 404 },
        { "BobTheBuilder/works/fgsfdssdavcrsacasfrfsd", 404 },
        { "users/BobTheBuilder/works/fgsfdssdavcrsacasfrfsd", 404 },
        { "BobTheBuilder/fgsfdssdavcrsacasfrfsd/", 404 },
        { "users/BobTheBuilder/fgsfdssdavcrsacasfrfsd/", 404 },
        { "BobTheBuilder/works/fgsfdssdavcrsacasfrfsd/", 404 },
        { "users/BobTheBuilder/works/fgsfdssdavcrsacasfrfsd/", 404 },

        { "asdfasdfadsfdasfads/fgsfdssdavcrsacasfrfsd", 404 },
        { "users/asdfasdfadsfdasfads/fgsfdssdavcrsacasfrfsd", 404 },
        { "asdfasdfadsfdasfads/works/fgsfdssdavcrsacasfrfsd", 404 },
        { "users/asdfasdfadsfdasfads/works/fgsfdssdavcrsacasfrfsd", 404 },
        { "asdfasdfadsfdasfads/fgsfdssdavcrsacasfrfsd/", 404 },
        { "users/asdfasdfadsfdasfads/fgsfdssdavcrsacasfrfsd/", 404 },
        { "asdfasdfadsfdasfads/works/fgsfdssdavcrsacasfrfsd/", 404 },
        { "users/asdfasdfadsfdasfads/works/fgsfdssdavcrsacasfrfsd/", 404 },

        // Create (not logged in)
        { "create", 403 },
        { "create/", 403 },

        // Misc.
        { "about", 200 },
        { "about/", 200 },
        { "terms", 200 },
        { "terms/", 200 },
        { "privacy", 200 },
        { "privacy/", 200 },
        { "copyright", 200 },
        { "copyright/", 200 },
        { "settings", 501 },
        { "settings/", 501 },
        { "find", 200 },
        { "find/", 200 },
        { "login", 200 },
        { "login/", 200 },
        { "validate", 200 },
        { "validate/", 200 },
    });
  }

  @Parameter(value = 0)
  public String route;

  @Parameter(value = 1)
  public int returnCode;

  @Before
  public void before() {
    ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
  }

  @Test
  public void testParameterizedInputFollowsConventions() {
    assertThat(route, not(startsWith("/")));
    assertThat(HTTP_CODES, hasItem(returnCode));
  }

  @Test
  public void testResponseCode() {
    String url = getServerAddress() + route;

    Map<String, String> headers = new HashMap<>();
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertEquals(returnCode, response.getStatusLine().getStatusCode());
  }

  private static final Collection<Integer> HTTP_CODES = Arrays.asList(
      100,
      101,
      102,
      200,
      201,
      202,
      203,
      204,
      205,
      206,
      207,
      208,
      226,
      300,
      301,
      302,
      303,
      304,
      305,
      307,
      308,
      400,
      401,
      402,
      403,
      404,
      405,
      406,
      407,
      408,
      409,
      410,
      411,
      412,
      413,
      414,
      415,
      416,
      417,
      418,
      421,
      422,
      423,
      424,
      426,
      428,
      429,
      431,
      444,
      451,
      499,
      500,
      501,
      502,
      503,
      504,
      505,
      506,
      507,
      508,
      510,
      511,
      599);
}
