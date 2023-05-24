package com.KoreaIT.JAM;


import util.DBUtil;
import util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
    private int id;
    public void run() {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

            conn = DriverManager.getConnection(url, "root", "");

            while (true) {
                System.out.printf("명령어) =>  ");
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

                    SecSql sql = new SecSql();
                    sql.append("INSERT INTO article");
                    sql.append("SET regDate = NOW(),");
                    sql.append("updateDate = NOW(),");
                    sql.append("title = ?", title);
                    sql.append(", `body` = ?", body);

                    int articleId = DBUtil.insert(conn, sql);
                    System.out.printf("%d번 게시글이 생성되었습니다.\n", articleId);

                } else if (cmd.equals("article list")) {
                    System.out.println("== 게시글 목록 == ");
                    List<Article> articleList = new ArrayList<>();

                    SecSql sql = new SecSql();
                    sql.append("SELECT *");
                    sql.append("FROM article");
                    sql.append("ORDER BY ID DESC");
                    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

                    for (Map<String, Object> articleMap : articleListMap) {
                        articleList.add(new Article(articleMap));
                    }

                    if (articleList.size() == 0) {
                        System.out.println("존재하는 게시글이 없습니다.");
                    }

                    for (Article article : articleList) {
                        System.out.printf("%d   |   %s  |   %s    |  %s\n", article.id, article.title, article.regDate, article.updateDate);
                    }


                } else if (cmd.startsWith("article modify ")) {
                    String[] cmdBist = cmd.split(" ");
                    try {
                        id = Integer.parseInt(cmdBist[2]);
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                        continue;
                    }

                    SecSql sql = SecSql.from("SELECT COUNT(*)");
                    sql.append("FROM article");
                    sql.append("WHERE id = ?", id);

                    int foundArticle = DBUtil.selectRowIntValue(conn, sql);

                    if (foundArticle == 0) {
                        System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
                        continue;
                    }

                    System.out.printf("== %d 번 게시글이 수정 == \n", id);
                    System.out.printf("수정할 title =>    ");
                    String title = sc.nextLine();
                    System.out.printf("수정할 body =>     ");
                    String body = sc.nextLine();

                    sql = new SecSql();
                    sql.append("UPDATE article");
                    sql.append("SET updateDate = NOW(),");
                    sql.append("title = ?", title);
                    sql.append(", `body` = ?", body);
                    sql.append("where id = ?", id);

                    DBUtil.update(conn, sql);

                    System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
                } else if (cmd.startsWith("article detail ")) {
                    try {
                        id = Integer.parseInt(cmd.split(" ")[2]);
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                        continue;
                    }

                    System.out.printf("== %s번 게시글 상세 보기 ==\n", id);

                    SecSql sql = new SecSql();
                    sql.append("SELECT *");
                    sql.append("FROM article");
                    sql.append("WHERE id = ?", id);
                    sql.append("ORDER BY id DESC");

                    Map<String, Object> foundArticle = DBUtil.selectRow(conn, sql);

                    for (String keys : foundArticle.keySet()) {
                        Object values = foundArticle.get(keys);
                        System.out.println(keys + "   :  " + values);
                    }

                } else if (cmd.startsWith("article delete ")) {
                    try {
                        id = Integer.parseInt(cmd.split(" ")[2]);
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                        continue;
                    }

                    SecSql sql = new SecSql();
                    sql.append("DELETE FROM article");
                    sql.append("WHERE id = ?", id);
                    int delArticle = DBUtil.delete(conn, sql);

                    if (delArticle == 0){
                        System.out.printf("%d번 글이 존재하지 않습니다.\n", id);
                        continue;
                    }
                    System.out.printf("%d번 글이 삭제되었습니다.\n", id);
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
