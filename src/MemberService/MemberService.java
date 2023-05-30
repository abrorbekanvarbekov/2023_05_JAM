package MemberService;

import MemberDao.MemberDao;

import java.sql.Connection;

public class MemberService {
    private MemberDao memberDao;
    public MemberService(Connection conn) {
        this.memberDao = new MemberDao(conn);
    }

    public boolean isLoginIdDup(String loginId) {
        return memberDao.isLoginIdDup(loginId);
    }

    public void doJoinQuery(String loginId, String loginPw, String name) {
        memberDao.doJoinQuery(loginId, loginPw, name);
    }
}
