package JDBCConnect_Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUpdateTest {
    public int id;
    public String title;
    public String body;

    public JDBCUpdateTest(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public void main() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");


            String sql = "UPDATE article";
            sql +=  " SET updateDate = NOW(),";
            sql +=  String.format(" title = '%s',",this.title);
            sql +=  String.format(" `body` = '%s'", this.body);
            sql += String.format("where id = %d;", this.id);

            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        } finally {
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
        }
    }
}
