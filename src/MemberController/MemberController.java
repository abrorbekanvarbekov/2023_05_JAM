package MemberController;

import MemberService.MemberService;
import Session.Session;
import com.KoreaIT.JAM.Member;
import util.Util;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class MemberController {
    private Scanner sc;
    private MemberService memberService;

    public MemberController(Connection conn, Scanner sc) {
        this.sc = sc;
        this.memberService = new MemberService(conn);
    }

    public void doJoin() {
        if (Session.session != null){
            System.out.println("로그인 아웃 후 사용해주세요!");
            return;
        }
        System.out.println("== 회원 가입 == ");
        String loginId = null;
        String loginPw = null;
        String name = null;

        while (true) {
            System.out.println("로그인 아이디 =>  ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0) {
                System.out.println("로그인 아이디를 입력하세요.");
                continue;
            }

            boolean loginIdIsDup = memberService.isLoginIdDup(loginId);

            if (loginIdIsDup) {
                System.out.printf("%s는 이미 존재하는 아이디 입니다.\n", loginId);
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("로그인 비밀번호 =>  ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0) {
                System.out.println("로그인 비밀번호를 입력하세요.");
                continue;
            }
            boolean loginPwCheck = true;
            while (true) {
                System.out.println("비밀번호 확인 =>  ");
                String checkPassword = sc.nextLine().trim();

                if (checkPassword.length() == 0) {
                    System.out.println("로그인 비밀번호 확인을 입력하세요.");
                    continue;
                }

                if (loginPw.equals(checkPassword) == false) {
                    System.out.println("비밀번호가 일치하지 않습니다.");
                    loginPwCheck = false;
                }
                break;
            }
            if (loginPwCheck) {
                break;
            }
        }

        while (true) {
            System.out.println("이름 =>  ");
            name = sc.nextLine().trim();

            if (name.length() == 0) {
                System.out.println("이름을 입력하세요.");
                continue;
            }
            break;
        }

        memberService.doJoinQuery(loginId, loginPw, name);
    }

    public void doLogin() {
        if (Session.session != null){
            System.out.println("로그인 아웃 후 사용해주세요!");
            return;
        }
        System.out.println("== 로그인 페이지 == ");
        String loginId = null;
        String loginPw = null;
        while (true) {
            System.out.printf("로그인 아아디 =>  ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0){
                System.out.println("로그인 아이디를 입력해주세요.");
                continue;
            }

            while (true){
                System.out.printf("로그인 비밀번호 =>  ");
                loginPw = sc.nextLine();

                if (loginPw.length() == 0){
                    System.out.println("로그인 비밀번호를 입력해주세요.");
                    continue;
                }
                break;
            }
            break;
        }

        Member member = memberService.isLoginIdExist(loginId);
        if (member == null){
            System.out.printf("%s는 아이디가 존재하지 않습니다.\n");
            return;
        }

        if (member.loginPw.equals(loginPw) == false){
            System.out.println("로그인 비밀번호가 일치하지 않습니다.");
            return;
        }
        Session.session = member;
        System.out.printf("%s님 환영합니다.\n", member.loginId);
    }

    public void doLogout() {
        Session.session = null;
        System.out.println("로그인 아웃 되었습니다.");
    }

    public void showMemberList() {
        System.out.println("== 회원 목록 == ");
        List<Member> memberList = memberService.getMemberList();
        for (Member member : memberList) {
            System.out.printf("%d   |   %s  |   %s    |  %s\n", member.id, member.loginId, Util.datetimeFormat(member.regDate), Util.datetimeFormat(member.updateDate));
        }
    }

}
