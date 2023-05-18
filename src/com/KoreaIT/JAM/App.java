package com.KoreaIT.JAM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    List<Article> articleList = null;

    public void run() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            while (true) {
                System.out.printf("명령어)");
                String cmd = sc.nextLine().trim();

                if (cmd.equals("exit")) {
                    System.out.println("== 프로그램 종료 ==");
                    break;
                }

                if (cmd.equals("article write")) {
                    System.out.println("== 게시글 작성 == ");
                    System.out.printf("title ni kiriting =>    ");
                    String title = sc.nextLine();
                    System.out.printf("bodyni kiriting =>   ");
                    String body = sc.nextLine();
                    try {
                        String sql = "INSERT INTO article";
                        sql += "   SET regDate = NOW(),";
                        sql += " updateDate = NOW(),";
                        sql += String.format(" title = '%s',", title);
                        sql += String.format(" `body` = '%s';", body);

                        pstmt = conn.prepareStatement(sql);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("에러: " + e);
                    }
                    System.out.println("게시글이 생성되었습니다.");

                } else if (cmd.equals("article list")) {
                    System.out.println("== 게시글 목록 == ");
                    ResultSet rs = null;

                    articleList = new ArrayList<>();
                    try {
                        String sql = "SELECT * FROM article";
                        sql += " ORDER BY id DESC;";
                        pstmt = conn.prepareStatement(sql);
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String title = rs.getString("title");
                            String body = rs.getString("body");
                            String regDate = rs.getString("regDate");
                            String updateDate = rs.getString("updateDate");
                            Article article = new Article(id, title, body, regDate, updateDate);
                            articleList.add(article);
                        }
                        if (articleList.size() == 0) {
                            System.out.println("존재하는 게시글이 없습니다.");
                        }

                        for (Article article : articleList) {
                            System.out.printf("%d   |   %s  |   %s    |  %s\n", article.id, article.title, article.regDate, article.updateDate);
                        }
                    } catch (SQLException e) {
                        System.out.println("에러  :" + e);
                    } finally {
                        try {
                            if (rs != null && !rs.isClosed()) {
                                rs.close();
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                } else if (cmd.startsWith("article modify ")) {
                    String[] cmdBist = cmd.split(" ");
                    if (cmdBist.length <= 2) {
                        System.out.println("명령어를 확인해주세요.");
                        continue;
                    }
                    int id = Integer.parseInt(cmdBist[2]);
                    Article foundArticle = null;

                    for (Article article : articleList) {
                        if (article.id == id) {
                            foundArticle = article;
                            break;
                        }
                    }
                    if (foundArticle == null) {
                        System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
                        continue;
                    }

                    System.out.printf("== %d 번 게시글이 수정 == \n", id);
                    System.out.printf("수정할 title =>    ");
                    String title = sc.nextLine();
                    System.out.printf("수정할 body =>     ");
                    String body = sc.nextLine();
                    try {
                        String sql = "UPDATE article";
                        sql += " SET updateDate = NOW(),";
                        sql += String.format(" title = '%s',", title);
                        sql += String.format(" `body` = '%s'", body);
                        sql += String.format("where id = %d;", id);

                        pstmt = conn.prepareStatement(sql);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("에러  :" + e);
                    }
                    System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
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
        sc.close();
    }
}
