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
import ninja.SecureFilter;
import ninja.appengine.AppEngineFilter;
import www.purple.mixxy.dao.ArticleDao;
import www.purple.mixxy.etc.LoggedInUser;
import www.purple.mixxy.models.ArticleDto;
import www.purple.mixxy.models.ArticlesDto;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith(AppEngineFilter.class)
public class ApiController {

    @Inject
    @SuppressWarnings("PMD.DefaultPackage")
    ArticleDao articleDao;
    
    public Result getArticlesJson() {
        
        ArticlesDto articlesDto = articleDao.getAllArticles();
        
        return Results.json().render(articlesDto);
        
    }
    
    public Result getArticlesXml() {
        
        ArticlesDto articlesDto = articleDao.getAllArticles();
        
        return Results.xml().render(articlesDto);
        
    }
    
    @FilterWith(SecureFilter.class)
    public Result postArticleJson(@LoggedInUser String username,
                              ArticleDto articleDto) {
        
        boolean succeeded = articleDao.postArticle(username, articleDto);
        
        if (succeeded) {
            return Results.json();
        } else {
            return Results.notFound();
        }
        
    }
    
    @FilterWith(SecureFilter.class)
    public Result postArticleXml(@LoggedInUser String username,
                                 ArticleDto articleDto) {        
        
        boolean succeeded = articleDao.postArticle(username, articleDto);
        
        if (succeeded) {
            return Results.xml();
        } else {
            return Results.notFound();
        }
        
    }
}
