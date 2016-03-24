package www.purple.mixxy.dao;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.models.Article;
import www.purple.mixxy.models.ArticleDto;
import www.purple.mixxy.models.ArticlesDto;
import www.purple.mixxy.models.User;

public class ArticleDao {
    
    @Inject
    private Provider<Objectify> objectify;
    
    public ArticlesDto getAllArticles() {
        
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.articles = objectify.get().load().type(Article.class).list();
        
        return articlesDto;
        
    }
    
    
    public Article getFirstArticleForFrontPage() {
        
        Article frontPost = objectify.get().load().type(Article.class).order("-postedAt").first()
                .now();
        return frontPost;
        
        
    }
    
    public List<Article> getOlderArticlesForFrontPage() {

        List<Article> olderPosts = objectify.get().load().type(Article.class).order("-postedAt")
                .offset(1).limit(10).list();
        
        return olderPosts;
        
    }
    
    
    public Article getArticle(Long id) {
        
        Article article = objectify.get().load().type(Article.class).id(id)
                .now();
        
        return article;
        
    }
    
    /**
     * @param username Name of the user who is posting this Article
     * @param articleDto ArticleDto that contains Article's content
     * @return false if user cannot be found in database.
     */
    public boolean postArticle(String username, ArticleDto articleDto) {
        
        User user = objectify.get().load().type(User.class).filter("username", username).first().now();
        
        if (user == null) {
            return false;
        }
        
        Article article = new Article(user, articleDto.title, articleDto.content);
        objectify.get().save().entity(article);
        
        return true;
        
    }

}
