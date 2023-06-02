package ArticleDao;

import util.DBUtil;
import util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    private Connection conn;

    public ArticleDao(Connection conn) {
        this.conn = conn;
    }

    public int doWrite(String title, String body, int session_id) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("memberId = ?", session_id);
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);

        return DBUtil.insert(conn, sql);
    }

    public List<Map<String, Object>> getArticleList() {
        SecSql sql = new SecSql();
        sql.append("SELECT A.* , M.name");
        sql.append("FROM article as A");
        sql.append("INNER JOIN  `member` as M");
        sql.append("ON A.memberId = M.id");
        sql.append("ORDER BY A.id DESC;");

        return DBUtil.selectRows(conn, sql);
    }

    public Map<String, Object> getArticle(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT A.* , M.name");
        sql.append("FROM article as A");
        sql.append("INNER JOIN  `member` as M");
        sql.append("ON A.memberId = M.id");
        sql.append("WHERE A.id = ?", id);

        return DBUtil.selectRow(conn, sql);
    }

    public int getArticleCount(int id) {
        SecSql sql = SecSql.from("SELECT COUNT(*)");
        sql.append("FROM article");
        sql.append("WHERE id = ?", id);

        return DBUtil.selectRowIntValue(conn, sql);
    }

    public void doModify(String title, String body, int id) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW(),");
        sql.append("title = ?", title);
        sql.append(", `body` = ?", body);
        sql.append("where id = ?", id);

        DBUtil.update(conn, sql);
    }

    public Map<String, Object> getArticleMap(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM article");
        sql.append("WHERE id = ?", id);

        return DBUtil.selectRow(conn, sql);
    }

    public void remove(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?", id);
        DBUtil.delete(conn, sql);
    }
}
