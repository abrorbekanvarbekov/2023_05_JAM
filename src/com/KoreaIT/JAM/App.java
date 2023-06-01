package com.KoreaIT.JAM;
import ArticleController.ArticleController;
import MemberController.MemberController;

import java.sql.*;
import java.util.*;

public class App {
    private int id;
    private List<Member> memberList = new ArrayList<>();

    public void run() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            MemberController memberController = new MemberController(conn, sc);
            ArticleController articleController = new ArticleController(conn, sc);
            while (true) {
                System.out.printf("명령어) =>  ");
                String cmd = sc.nextLine().trim();

                if (cmd.equals("exit")) {
                    System.out.println("== 프로그램 종료 ==");
                    break;
                }

                if (cmd.equals("member join")) {
                    if (MemberController.session == true){
                        System.out.println("로그인 아웃 후 사용해주세요!");
                        continue;
                    }
                    memberController.doJoin();
                } else if (cmd.equals("member login")) {
                    if (MemberController.session == true){
                        System.out.println("로그인 아웃 후 사용해주세요!");
                        continue;
                    }
                    memberController.doLogin();
                } else if (cmd.equals("member logout")) {
                    MemberController.session = false;
                    System.out.println("로그인 아웃 되었습니다.");
                } else if (cmd.equals("article write")) {
                    if (MemberController.session == false){
                        System.out.println("로그인 후 사용해주세요!");
                        continue;
                    }
                    articleController.doWrite();
                } else if (cmd.equals("article list")) {
                    articleController.showArticleList();
                } else if (cmd.startsWith("article modify ")) {
                    if (MemberController.session == false){
                        System.out.println("로그인 후 사용해주세요!");
                        continue;
                    }
                    articleController.doModify(cmd);
                } else if (cmd.startsWith("article detail ")) {
                    articleController.showArticleDetail(cmd);
                } else if (cmd.startsWith("article delete ")) {
                    if (MemberController.session == false){
                        System.out.println("로그인 후 사용해주세요!");
                        continue;
                    }
                    articleController.doDelete(cmd);
                } else {
                    System.out.println("잘못 된 명령어 입니다.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }

}
