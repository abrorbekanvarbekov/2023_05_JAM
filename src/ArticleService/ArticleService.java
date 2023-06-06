package ArticleService;

import ArticleDao.ArticleDao;
import com.KoreaIT.JAM.Article;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
    }


    public int doWrite(String title, String body, int session_id) {
        return articleDao.doWrite(title, body, session_id);
    }

    public List<Article> getArticleList(String searchKeyword) {
        List<Map<String, Object>> articleListMap = articleDao.getArticleList(searchKeyword);

        List<Article> articleList = new ArrayList<>();
        for (Map<String, Object> articleMap : articleListMap) {
            articleList.add(new Article(articleMap));
        }

        return  articleList;
    }

    public Article getArticleCount(int id) {
        Map<String, Object> foundArticleMap = articleDao.getArticle(id);

        if (foundArticleMap.isEmpty()) {
            return null;
        }
        return new Article(foundArticleMap);
    }

    public void doModify(String title, String body, int id) {
        articleDao.doModify(title, body, id);
    }

    public Article getArticleMap(int id) {
        Map<String, Object> foundArticleMap = articleDao.getArticle(id);

        if (foundArticleMap.isEmpty()) {
            return null;
        }
        return new Article(foundArticleMap);
    }

    public void remove(int id) {
        articleDao.remove(id);
    }

    public int increaseVCnt(int id) {
       return articleDao.increaseVCnt(id);
    }
}
