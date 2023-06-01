package MemberService;

import MemberDao.MemberDao;
import com.KoreaIT.JAM.Article;
import com.KoreaIT.JAM.Member;

import java.sql.Connection;
import java.util.Map;

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

    public Member isLoginIdExist(String loginId) {
        Map<String, Object> memberMap = memberDao.isLoginIdExist(loginId);
        if (memberMap.isEmpty()){
            return null;
        }
        Member member = new Member(memberMap);
        return member;
    }

}
