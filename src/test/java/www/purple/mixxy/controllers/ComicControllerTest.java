package www.purple.mixxy.controllers;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

import ninja.NinjaAppengineBackendTest;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.models.Comic;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ComicControllerTest extends NinjaAppengineBackendTest {

  private ComicDao comicDao;

  @Before
  public void before() {
    Injector injector = this.getInjector();
    ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
    comicDao = injector.getInstance(ComicDao.class);
  }

  @Test
  public void testComicDaoLoaded() {
    assertNotNull(comicDao);
  }

  @Test
  public void testImageContentTypeIsAppropriate() {
    List<Comic> comics = comicDao.getComics("BobTheBuilder");

    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/" + comics.get(0).sluggedTitle + "/image";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertThat(response.getFirstHeader("Content-Type").getValue(), startsWith("image/"));
  }

  @Test
  public void testValidComicPageReturnsOk() {
    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/cool-title";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertEquals(200, response.getStatusLine().getStatusCode());
  }

  @Test
  public void testValidComicImageReturnsOk() {
    List<Comic> comics = comicDao.getComics("BobTheBuilder");

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
  public void testInvalidComicValidUserImageUrlReturns404() {
    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/BobTheBuilder/fgsfdssdavcrsacasfrfsd/image";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

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

  @Test
  public void testInvalidUserInComicImageUrlReturns404() {
    Map<String, String> headers = new HashMap<>();
    String url = getServerAddress() + "users/eawruwehnciieuiwf/fgsfdssdavcrsacasfrfsd/image";
    HttpResponse response = ninjaTestBrowser.makeRequestAndGetResponse(url, headers);

    assertEquals(404, response.getStatusLine().getStatusCode());
  }
}
