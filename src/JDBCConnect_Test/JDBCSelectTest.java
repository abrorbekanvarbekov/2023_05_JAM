package JDBCConnect_Test;

import com.KoreaIT.JAM.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
    public List<Article> articleList = new ArrayList<>();

    public List<Article> main() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT * FROM article";
            sql +=  " ORDER BY id DESC;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String body = rs.getString("body");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                Article article = new Article(id, title, body, regDate, updateDate);
                articleList.add(article);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (articleList.size() == 0){
                System.out.println("존재하는 게시글이 없습니다.");
            }

            for (Article article : articleList){
                System.out.printf("%d   |   %s  |   %s    |  %s\n", article.id, article.title, article.regDate, article.updateDate);
            }
        }
        return articleList;
    }
}
