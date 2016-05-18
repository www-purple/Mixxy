package www.purple.mixxy.controllers;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import ninja.NinjaAppengineBackendTest;
import ninja.NinjaRunner;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.models.Comic;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(NinjaRunner.class)
public class ComicControllerTest extends NinjaAppengineBackendTest {

  private static final String USER = "BobTheBuilder";

  @Inject
  private ComicDao comicDao;

  @Before
  public void before() {
    ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
  }

  @Test
  public void testComicDaoLoaded() {
    assertNotNull(comicDao);
  }

  @Test
  public void testImageContentTypeIsAppropriate() {
    List<Comic> comics = comicDao.getComics(USER);

    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/" + comics.get(0).sluggedTitle + "/image";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertThat(response.getFirstHeader("Content-Type").getValue(), startsWith("image/"));
  }

  @Test
  public void testMultipleRoutesToComicPageAreTheSame() {
    //"(?:/users)?/{user}(?:/works)?/{work}/?"

    String[] urls = {
        getServerAddress() + "BobTheBuilder/cool-title",
        getServerAddress() + "users/BobTheBuilder/cool-title",
        getServerAddress() + "BobTheBuilder/works/cool-title",
        getServerAddress() + "users/BobTheBuilder/works/cool-title",
    };

    String[] results = new String[urls.length];

    for (int i = 0; i < results.length; ++i) {
      results[i] = ninjaTestBrowser.makeRequest(urls[i]).substring(0, 1024);
      // substring because we don't want to include the part of the page with the timestamp
    }

    for (String result : results) {
      assertEquals(results[0], result);
    }
  }

  @Test
  public void testValidComicImageReturnsOk() {
    List<Comic> comics = comicDao.getComics(USER);

    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/cool-title/image";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertEquals(200, response.getStatusLine().getStatusCode());
  }

  @Test
  public void testInvalidComicValidUserReturns404() {
    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/fgsfdssdavcrsacasfrfsd";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertThat(response.getEntity().getContentType().getValue(), not(startsWith("image/")));
    assertEquals(404, response.getStatusLine().getStatusCode());
  }

  @Test
  public void testInvalidUserInComicUrlReturns404() {
    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/eawruwehnciieuiwf/fgsfdssdavcrsacasfrfsd";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertThat(response.getEntity().getContentType().getValue(), not(startsWith("image/")));
    assertEquals(404, response.getStatusLine().getStatusCode());
  }
}
