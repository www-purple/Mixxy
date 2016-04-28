package www.purple.mixxy.controllers;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import www.purple.mixxy.dao.ArticleDao;
import www.purple.mixxy.etc.LoggedInUser;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.models.Article;
import www.purple.mixxy.models.ArticleDto;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class ArticleController {
    
    @Inject
    private ArticleDao articleDao;

    ///////////////////////////////////////////////////////////////////////////
    // Show article
    ///////////////////////////////////////////////////////////////////////////
    public Result articleShow(@PathParam("id") Long id) {

        Article article = null;

        if (id != null) {

            article = articleDao.getArticle(id);

        }

        return Results.html().render("article", article);

    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Create new article
    ///////////////////////////////////////////////////////////////////////////
    @FilterWith(SecureFilter.class)
    public Result articleNew() {

        return Results.html();

    }

    @FilterWith(SecureFilter.class)
    public Result articleNewPost(@LoggedInUser String username,
                                 Context context,
                                 @JSR303Validation ArticleDto articleDto,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashScope().error("Please correct field.");
            context.getFlashScope().put("title", articleDto.title);
            context.getFlashScope().put("content", articleDto.content);

            return Results.redirect("/article/new");

        } else {
            
            articleDao.postArticle(username, articleDto);
            
            context.getFlashScope().success("New article created.");
            
            return Results.redirect("/");

        }

    }

}
