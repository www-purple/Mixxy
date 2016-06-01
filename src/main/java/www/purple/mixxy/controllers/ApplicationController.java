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

package www.purple.mixxy.controllers;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.dao.ComicDao;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Comic;
import www.purple.mixxy.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.appengine.AppEngineFilter;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ApplicationController {

  @Inject
  private ComicDao comicDao;

  @Inject
  private UserDao userDao;

  /**
   * Method to put initial data in the db...
   *
   * @return A successful Result
   */
  public Result setup() {

    ObjectifyProvider.setup();

    return Results.ok();
  }

  public Result index() {
    List<Comic> recent = comicDao.getMostRecentComics(10);

    Map<String, User> authorToComic = new HashMap<>();
    for (Comic c : recent) {
      User user = userDao.getUser(c.authorId);
      authorToComic.put(c.authorId.toString(), user);
    }
    return Results.ok().html().render("latest_comics", recent).render("id_to_user", authorToComic);

  }

}
