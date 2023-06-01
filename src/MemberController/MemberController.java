package MemberController;

import MemberService.MemberService;
import com.KoreaIT.JAM.Member;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    private Scanner sc;
    private MemberService memberService;
    public static Member session;

    public MemberController(Connection conn, Scanner sc) {
        this.sc = sc;
        this.memberService = new MemberService(conn);
        this.session = null;
    }

    public void doJoin() {
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
            System.out.println("로그인 아이디가 존재하지 않습니다.");
            return;
        }

        if (member.loginPw.equals(loginPw) == false){
            System.out.println("로그인 비밀번호가 일치하지 않습니다.");
            return;
        }
        session = member;
        System.out.printf("%s님 환영합니다.\n", member.loginId);
    }
}
