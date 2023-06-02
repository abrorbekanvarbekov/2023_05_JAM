package ArticleController;

import ArticleService.ArticleService;
import Session.Session;
import com.KoreaIT.JAM.Article;
import util.Util;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
    private Scanner sc;
    private int id;
    private ArticleService articleService;
    public ArticleController(Connection conn, Scanner sc) {
        this.sc = sc;
        this.articleService = new ArticleService(conn);
    }

    public void doWrite() {
        if (Session.session == null){
            System.out.println("로그인 후 사용해주세요!");
            return;
        }
        System.out.println("== 게시글 작성 == ");
        System.out.printf("title ni kiriting =>    ");
        String title = sc.nextLine();
        System.out.printf("bodyni kiriting =>   ");
        String body = sc.nextLine();

        int articleId = articleService.doWrite(title, body, Session.session.id);

        System.out.printf("%d번 게시글이 생성되었습니다.\n", articleId);
    }

    public void showArticleList() {
        System.out.println("== 게시글 목록 == ");

        List<Article> articleList  = articleService.getArticleList();

        if (articleList.size() == 0) {
            System.out.println("존재하는 게시글이 없습니다.");
        }

        System.out.println("번호    |    제목    |    등록날짜    |   수정 날짜");
        for (Article article : articleList) {
            System.out.printf("%d   |   %s  |   %s    |  %s    |  %s\n", article.id, article.title, Util.datetimeFormat(article.regDate), Util.datetimeFormat(article.updateDate), article.writer);
        }
    }

    public void doModify(String cmd) {
        if (Session.session == null){
            System.out.println("로그인 후 사용해주세요!");
            return;
        }
        String[] cmdBist = cmd.split(" ");
        try {
            id = Integer.parseInt(cmdBist[2]);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }

        int foundArticle = articleService.getArticleCount(id);

        if (foundArticle == 0) {
            System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("== %d 번 게시글이 수정 == \n", id);
        System.out.printf("수정할 title =>    ");
        String title = sc.nextLine();
        System.out.printf("수정할 body =>     ");
        String body = sc.nextLine();

        articleService.doModify(title, body, id);

        System.out.printf("%d번 게시글은 수정되었습니다.\n", id);
    }

    public void showArticleDetail(String cmd) {
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }

        Article article = articleService.getArticleMap(id);
        if (article == null){
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("== %s번 게시글 상세 보기 ==\n", id);
        article.getInfo();
    }

    public void doDelete(String cmd) {
        if (Session.session == null){
            System.out.println("로그인 후 사용해주세요!");
            return;
        }
        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return;
        }

        int foundArticle = articleService.getArticleCount(id);

        if (foundArticle == 0) {
            System.out.printf("%d번 글은 존재하지 않습니다.\n", id);
            return;
        }

        articleService.remove(id);

        System.out.printf("%d번 글이 삭제되었습니다.\n", id);
    }

}
