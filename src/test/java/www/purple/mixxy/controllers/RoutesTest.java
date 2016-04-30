/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.purple.mixxy.controllers;

import ninja.AssetsController;
import ninja.NinjaTest;
import ninja.Route;
import ninja.Router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * This test class is <em>only</em> for anything involving the routes; for the
 * server's reaction to actually navigating to these routes, see
 * {@link NavigationTest}.
 *
 * @author Jesse Talavera-Greenberg
 * @see NavigationTest
 */
public class RoutesTest extends NinjaTest {

  @Inject
  private Router router;

  @Before
  public void injectRouter() {
    Injector injector = getInjector();
    router = injector.getInstance(Router.class);
  }

  @Test
  public void testRouterAvailable() {
    assertNotNull(router);
  }

  @Test
  public void testInvalidRoutesHaveNoController() {
    Route route = router.getRouteFor("DELETE", "/find/sadfasfadsfasd/asdfasdfsadfjasd");

    assertNull(route);
  }

  @Test
  public void testAssetsRouteToAssetsController() {
    Route route = router.getRouteFor("GET", "/assets/image.png");

    assertEquals(AssetsController.class, route.getControllerClass());
    assertEquals("serveStatic", route.getControllerMethod().getName());
  }

  @Test
  public void testNoPostRouteToAssets() {
    Route route = router.getRouteFor("POST", "/assets/image.png");

    assertNull(route);
  }

  @Test
  public void testUserRouteShortcut() {
    Route a = router.getRouteFor("GET", "/JamesBond");
    Route b = router.getRouteFor("GET", "/users/JamesBond");

    assertSame(a, b);
  }

  @Test
  public void testAboutDoesntUseUserController() {
    Route a = router.getRouteFor("GET", "/about");

    assertNotSame(UserController.class, a.getControllerClass());
  }

  @Test
  public void testLoginMethodsRouteDifferently() {
    Route a = router.getRouteFor("GET", "/login");
    Route b = router.getRouteFor("POST", "/login");

    assertNotSame(a, b);
  }

  @Test
  public void testLoginUsesLoginController() {
    Route a = router.getRouteFor("GET", "/login");

    assertSame(LoginLogoutController.class, a.getControllerClass());
  }

  @Test
  public void testApiHeadRoutesToApiController() {
    Route a = router.getRouteFor("GET", "/api");

    assertSame(ApiController.class, a.getControllerClass());
  }

  @Test
  public void testLeadingSlashNeeded() {
    Route a = router.getRouteFor("GET", "api");

    assertNull(a);
  }

  @Test
  public void testTrailingSlashOptional() {
    Route a = router.getRouteFor("GET", "/api/");

    assertSame(ApiController.class, a.getControllerClass());
  }

  @Test
  public void testTrailingSlashResultsInSameRoute() {
    Route a = router.getRouteFor("GET", "/api/");
    Route b = router.getRouteFor("GET", "/api");

    assertSame(a, b);
  }

  @Test
  public void testSameRouteForIndex() {
    Route a = router.getRouteFor("GET", "");
    Route b = router.getRouteFor("GET", "/");

    assertSame(a, b);
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  public void testAllRoutesEndWithOptionalSlash() {
    for (final Route r : router.getRoutes()) {
      assertThat(r.getUrl(), endsWith("/?"));
    }
  }
}
