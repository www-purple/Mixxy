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

import www.purple.mixxy.controllers.ApiController;
import www.purple.mixxy.controllers.ApplicationController;
import www.purple.mixxy.controllers.ComicController;
import www.purple.mixxy.controllers.LoginLogoutController;
import www.purple.mixxy.controllers.SearchController;
import www.purple.mixxy.controllers.UserController;

/**
 * Defines all the mappings from URL patterns to controllers.
 *
 * @author Jesse Talavera-Greenberg
 * @TODO Can we load this from a file?
 */
public class Routes implements ApplicationRoutes {

  // Use this to route to static pages (e.g. for about, privacy policy, etc.)
  private static final String VIEWS = "www/purple/mixxy/views/";
  private static final String PATCH = "PATCH";

  @Inject
  private NinjaProperties ninjaProperties;

  /**
   * Initializes all Mixxy routes, used to decide which URLs should map to which controllers.
   *
   * @param router
   *          The default router of this application
   */
  @Override
  @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.AvoidDuplicateLiterals" })
  public void init(final Router router) {
    if (!ninjaProperties.isProd()) {
      // If this is not a production build...
      router.GET().route("/setup/?").with(ApplicationController.class, "setup");
    }

    // CHECKSTYLE:OFF
    router.GET().route("/?").with(ApplicationController.class, "index");

    ///////////////////////////////////////////////////////////////////////////
    // Assets (pictures / javascript)
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/assets/webjars/{fileName: .*}/?").with(AssetsController.class, "serveWebJars");
    router.GET().route("/assets/{fileName: .*}/?").with(AssetsController.class, "serveStatic");
    router.GET().route("/robots.txt/?").with(AssetsController.class, "serveStatic");

    ///////////////////////////////////////////////////////////////////////////
    // Meta pages
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/about/?").with(Results.html().template(VIEWS + "meta/about.ftl.html"));
    router.GET().route("/terms/?").with(Results.html().template(VIEWS + "meta/terms.ftl.html"));
    router.GET().route("/privacy/?").with(Results.html().template(VIEWS + "meta/privacy.ftl.html"));
    router.GET().route("/contact/?").with(Results.html().template(VIEWS + "meta/contact.ftl.html"));
    router.GET().route("/copyright/?").with(Results.html().template(VIEWS + "meta/copyright.ftl.html"));

    router.GET().route("/settings/?").with(UserController.class, "settings");

    ///////////////////////////////////////////////////////////////////////////
    // Search
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/find/?").with(SearchController.class, "search");

    ///////////////////////////////////////////////////////////////////////////
    // Login / Logout
    ///////////////////////////////////////////////////////////////////////////
    router.GET().route("/login/?").with(LoginLogoutController.class, "login");
    router.GET().route("/logout/?").with(LoginLogoutController.class, "logout");
    router.GET().route("/validate/?").with(LoginLogoutController.class, "validate");

    // Get information about the API (version) and Mixxy in general
    router.GET().route("/api/?").with(ApiController.class, "api");

    ///////////////////////////////////////////////////////////////////////////
    // API (current user)
    ///////////////////////////////////////////////////////////////////////////
    {

      router.GET().route("/api/user/?").with(UserController.class, "user");
      router.METHOD(PATCH).route("/api/user/?").with(UserController.class, "settings");
      // Get or update information about this user

      {
        // Gets the list of everyone subscribed to this user
        router.GET().route("/api/user/subscribers/?").with(UserController.class, "subscribers");

        router.GET().route("/api/user/subscribed/?").with(UserController.class, "subscribed");
        router.PUT().route("/api/user/subscribed/?").with(UserController.class, "subscribe");
        router.DELETE().route("/api/user/subscribed/?").with(UserController.class, "unsubscribe");
        // Gets all, adds one of, or removes one of this user's subscribers

        router.GET().route("/api/user/likes/?").with(UserController.class, "likes");
        router.PUT().route("/api/user/likes/?").with(UserController.class, "like");
        router.DELETE().route("/api/user/likes/?").with(UserController.class, "unlike");
        // Gets, adds to, or removes from the comics this user likes

        router.GET().route("/api/user/comics/?").with(UserController.class, "comics");
        router.POST().route("/api/user/comics/?").with(ComicController.class, "newComic");
        // Get the list of all comics this user has created, or submits a new
        // one (possibly with a URL with another comic to remix)

        {
          router.GET().route("/api/user/comics/{comic}/?").with(ComicController.class, "comic");
          router.METHOD(PATCH).route("/api/user/comics/{comic}/?").with(ComicController.class, "update");
          router.DELETE().route("/api/user/comics/{comic}/?").with(ComicController.class, "delete");
          // Gets, updates, or delete's a user's own comic

          {
            // Get the list of all users that like this comic
            router.GET().route("/api/user/comics/{comic}/likes/?").with(ComicController.class, "likes");

            // Get the list of all remixes
            router.GET().route("/api/user/comics/{comic}/remixes/?").with(ComicController.class, "remixes");

            // Get the parent, if any
            router.GET().route("/api/user/comics/{comic}/parent/?").with(ComicController.class, "parent");

            // Gets the comic this one is ultimately based on
            router.GET().route("/api/user/comics/{comic}/root/?").with(ComicController.class, "root");
          }
        }
      }
    }
    ///////////////////////////////////////////////////////////////////////////
    // API (any user)
    ///////////////////////////////////////////////////////////////////////////

    // Get information about any user
    {
      router.GET().route("/api/users/{user}/?").with(UserController.class, "user");

      {
        router.GET().route("/api/users/{user}/subscribers/?").with(UserController.class, "subscribers");

        router.GET().route("/api/users/{user}/subscribed/?").with(UserController.class, "subscribed");
        // Gets any user's subcriptions

        router.GET().route("/api/users/{user}/likes/?").with(UserController.class, "likes");
        // Get the list of all comics any user has liked

        router.GET().route("/api/users/{user}/comics/?").with(UserController.class, "comics");
        // Get a list of any user's comics

        {
          router.GET().route("/api/users/{user}/comics/{comic}/?").with(ComicController.class, "comic");
          // Get a specific comic from any user

          {
            router.GET().route("/api/users/{user}/comics/{comic}/image/?").with(ComicController.class, "image");

            router.GET().route("/api/users/{user}/comics/{comic}/remixes/?").with(ComicController.class, "remixes");
            // Get the remixes of any comic

            router.GET().route("/api/users/{user}/comics/{comic}/likes/?").with(ComicController.class, "likes");

            router.GET().route("/api/users/{user}/comics/{comic}/parent/?").with(ComicController.class, "parent");

            router.GET().route("/api/users/{user}/comics/{comic}/root/?").with(ComicController.class, "root");
          }
        }
      }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Site URLs (user-facing)
    ///////////////////////////////////////////////////////////////////////////

    // Page to create or upload a new comic
    router.GET().route("/create/?").with(ComicController.class, "newComic");

    // Page to create or upload a new comic
    router.GET().route("/create/muro/?").with(ComicController.class, "muro");
    router.GET().route("/create/upload/?").with(ComicController.class, "upload");

    // Page to actually submit a comic
    router.POST().route("/create/?").with(ComicController.class, "newComic");

    // User profile page
    router.GET().route("(?:/users)?/{user}/?").with(UserController.class, "user");

    // Page that lists a user's subscribers
    router.GET().route("(?:/users)?/{user}/subscribers/?").with(UserController.class, "subscribers");

    // Page that lists everyone a user is subscribed to
    router.GET().route("(?:/users)?/{user}/subscribed/?").with(UserController.class, "subscribed");

    // Page that lists all of a user's likes
    router.GET().route("(?:/users)?/{user}/likes/?").with(UserController.class, "likes");

    // Get a given comic from any user
    router.GET().route("(?:/users)?/{user}/comics/?").with(UserController.class, "comics");

    // Get a specific comic from any user
    router.GET().route("(?:/users)?/{user}(?:/comics)?/{comic}/?").with(ComicController.class, "comic");

    // Get the actual image from a given comic
    router.GET().route("(?:/users)?/{user}(?:/comics)?/{comic}/image/?").with(ComicController.class, "image");

    // Load the new remixing page
    router.GET().route("(?:/users)?/{user}(?:/comics)?/{comic}/remix/?").with(ComicController.class, "newRemix");

    // Submit the remixed comic
    router.POST().route("(?:/users)?/{user}(?:/comics)?/{comic}/remix/?").with(ComicController.class, "newRemix");

    // Load the remixes page
    router.GET().route("(?:/users)?/{user}(?:/comics)?/{comic}/remixes/?").with(ComicController.class, "remixes");
    // CHECKSTYLE:ON
  }

}
