package MemberDao;

import util.DBUtil;
import util.SecSql;

import java.sql.Connection;

public class MemberDao {
    private Connection conn;
    public MemberDao(Connection conn) {
        this.conn = conn;
    }

    public boolean isLoginIdDup(String loginId) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM member");
        sql.append("WHERE loginId = ?", loginId);
        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public void doJoinQuery(String loginId, String loginPw, String name) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO member");
        sql.append("SET regDate = now(),");
        sql.append("updateDate = now(),");
        sql.append("loginId = ?", loginId);
        sql.append(", loginPw = ?", loginPw);
        sql.append(", name = ?", name);
        DBUtil.insert(conn, sql);
        System.out.printf("%s님 환영합니다.\n", loginId);
    }

    public boolean isLoginIdExist(String loginId) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM member");
        sql.append("WHERE loginId = ?", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public boolean isLoginPwExist(String loginPw) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM member");
        sql.append("WHERE loginPw = ?", loginPw);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
}
