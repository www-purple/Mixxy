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
import www.purple.mixxy.controllers.ArticleController;
import www.purple.mixxy.controllers.LoginLogoutController;
import www.purple.mixxy.controllers.UserController;

public class Routes implements ApplicationRoutes {

  // Use this to route to static pages (e.g. for about, privacy policy, etc.)
  private static final String VIEWS = "www/purple/mixxy/views/";

  @Inject
  private NinjaProperties ninjaProperties;

  /**
   * Using a (almost) nice DSL we can configure the router.
   * 
   * The second argument NinjaModuleDemoRouter contains all routes of a
   * submodule. By simply injecting it we activate the routes.
   * 
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

    ///////////////////////////////////////////////////////////////////////
    // Meta pages
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/about").with(Results.html().template(VIEWS + "meta/about.ftl.html"));
    router.GET().route("/terms").with(Results.html().template(VIEWS + "meta/terms.ftl.html"));
    router.GET().route("/privacy").with(Results.html().template(VIEWS + "meta/privacy.ftl.html"));
    router.GET().route("/contact").with(Results.html().template(VIEWS + "meta/contact.ftl.html"));

    ///////////////////////////////////////////////////////////////////////
    // Login / Logout
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/login").with(LoginLogoutController.class, "login");
    router.POST().route("/login").with(LoginLogoutController.class, "loginPost");
    router.GET().route("/logout").with(LoginLogoutController.class, "logout");

    ///////////////////////////////////////////////////////////////////////
    // Users
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/api/user/{id}").with(UserController.class, "user");
    router.GET().route("/user/{id}").with(UserController.class, "user");

    ///////////////////////////////////////////////////////////////////////
    // Create new article
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/article/new").with(ArticleController.class, "articleNew");
    router.POST().route("/article/new").with(ArticleController.class, "articleNewPost");

    ///////////////////////////////////////////////////////////////////////
    // Create new article
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/article/{id}").with(ArticleController.class, "articleShow");

    ///////////////////////////////////////////////////////////////////////
    // Api for management of software
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/api/{username}/articles.json").with(ApiController.class, "getArticlesJson");
    router.GET().route("/api/{username}/articles.xml").with(ApiController.class, "getArticlesXml");
    router.POST().route("/api/{username}/article.json").with(ApiController.class, "postArticleJson");
    router.POST().route("/api/{username}/article.xml").with(ApiController.class, "postArticleXml");

    ///////////////////////////////////////////////////////////////////////
    // Assets (pictures / javascript)
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
    router.GET().route("/robots.txt").with(AssetsController.class, "serveStatic");

    ///////////////////////////////////////////////////////////////////////
    // Index / Catchall shows index page
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/.*").with(ApplicationController.class, "index");
  }

}
