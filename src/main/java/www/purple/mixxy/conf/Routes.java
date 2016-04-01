/**
 * Copyright (C) 2012 the original author or authors.
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

package www.purple.mixxy.conf;

import com.google.inject.Inject;

import ninja.AssetsController;
import ninja.Results;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;
import www.purple.mixxy.controllers.ApplicationController;
import www.purple.mixxy.controllers.ComicController;
import www.purple.mixxy.controllers.LoginLogoutController;
import www.purple.mixxy.controllers.UserController;

public class Routes implements ApplicationRoutes {

  // Use this to route to static pages (e.g. for about, privacy policy, etc.)
  private static final String VIEWS = "www/purple/mixxy/views/";
  private static final String PATCH = "PATCH";

  @Inject
  private NinjaProperties ninjaProperties;

  /**
   * @param router
   *          The default router of this application
   */
  @Override
  public void init(final Router router) {

    // puts test data into db:
    if (!ninjaProperties.isProd()) {
      router.GET().route("/setup").with(ApplicationController.class, "setup");
    }

    router.GET().route("/").with(ApplicationController.class, "index");

    ///////////////////////////////////////////////////////////////////////////
    // Assets (pictures / javascript)
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
    router.GET().route("/robots.txt").with(AssetsController.class, "serveStatic");

    ///////////////////////////////////////////////////////////////////////////
    // Meta pages
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/about").with(Results.html().template(VIEWS + "meta/about.ftl.html"));
    router.GET().route("/terms").with(Results.html().template(VIEWS + "meta/terms.ftl.html"));
    router.GET().route("/privacy").with(Results.html().template(VIEWS + "meta/privacy.ftl.html"));
    router.GET().route("/contact").with(Results.html().template(VIEWS + "meta/contact.ftl.html"));

    ///////////////////////////////////////////////////////////////////////////
    // Search
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/find").with(ComicController.class, "search");

    ///////////////////////////////////////////////////////////////////////////
    // Login / Logout
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/login").with(LoginLogoutController.class, "login");
    router.POST().route("/login").with(LoginLogoutController.class, "loginPost");
    router.GET().route("/logout").with(LoginLogoutController.class, "logout");
    router.GET().route("/signup").with(LoginLogoutController.class, "signup");
    router.POST().route("/signup").with(LoginLogoutController.class, "signupPost");

    ///////////////////////////////////////////////////////////////////////////
    // API (current user)
    ///////////////////////////////////////////////////////////////////////////
    {
      // Get all information about this user
      router.GET().route("/api/user").with(UserController.class, "user");

      // Updates information about this user
      router.METHOD(PATCH).route("/api/user").with(UserController.class, "userSettings");
    }

    {
      // Gets the list of everyone subscribed to this user
      router.GET().route("/api/user/subscribers").with(UserController.class, "subscribers");
    }

    {
      // Gets the list of everyone this user is subscribed to
      router.GET().route("/api/user/subscribed").with(UserController.class, "subscribed");

      // Adds/removes this user's subscriptions
      router.METHOD(PATCH).route("/api/user/subscribed").with(UserController.class, "subscribe");

      // Subscribe this user to another
      router.PUT().route("/api/user/subscribed").with(UserController.class, "subscribe");

      // Unsubscribes this user from another
      router.DELETE().route("/api/user/subscribed").with(UserController.class, "unsubscribe");
    }

    {
      // Get the list of all works this user has created
      router.GET().route("/api/user/works").with(UserController.class, "works");

      // Submits a new work on behalf of this user. (Request may contain the URL
      // for another work to remix.)
      router.POST().route("/api/user/works").with(ComicController.class, "newWork");
    }

    {
      // Get a specific work from this user
      router.GET().route("/api/user/work/{work}").with(UserController.class, "work");

      // Update a work from this user
      router.METHOD(PATCH).route("/api/user/work/{work}").with(ComicController.class, "update");

      // Removes a work from this user (but remixes will still exist)
      router.DELETE().route("/api/user/work/{work}").with(ComicController.class, "update");

      // The current user submits a remix for another person's work
      router.PUT().route("/api/users/{user}/work/{work}").with(ComicController.class, "remix");
    }

    {
      // Get the list of all works this user has liked
      router.GET().route("/api/user/likes").with(UserController.class, "likes");

      // Add or remove a like from this user
      router.METHOD(PATCH).route("/api/user/likes").with(UserController.class, "updateLikes");

      // This user likes a given work
      router.PUT().route("/api/user/likes").with(UserController.class, "like");

      // This user no longer likes a given work
      router.DELETE().route("/api/user/likes").with(UserController.class, "unlike");
    }

    ///////////////////////////////////////////////////////////////////////////
    // API (any user)
    ///////////////////////////////////////////////////////////////////////////

    // Get information about any user
    router.GET().route("/api/users/{user}").with(UserController.class, "user");

    router.GET().route("/api/users/{user}/subscribers").with(UserController.class, "subscribers");

    // Gets any user's subcriptions
    router.GET().route("/api/users/{user}/subscribed").with(UserController.class, "subscribed");

    // Get a given work from any user
    router.GET().route("/api/users/{user}/works").with(UserController.class, "works");

    // Get a specific work from any user
    router.GET().route("/api/users/{user}/work/{work}").with(UserController.class, "work");

    // Get the list of all works any user has liked
    router.GET().route("/api/users/{user}/likes").with(UserController.class, "likes");

    ///////////////////////////////////////////////////////////////////////////
    // Site URLs (user-facing)
    ///////////////////////////////////////////////////////////////////////////
    
    router.GET().route("/{user}").with(UserController.class, "user");
  }

}
