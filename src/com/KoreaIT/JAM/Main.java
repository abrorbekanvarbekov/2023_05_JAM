package com.KoreaIT.JAM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);
        int articleLastId = 0;
        List<Article> articleList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        while(true){
            System.out.printf("명령어)");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")){
                System.out.println("== 프로그램 종료 ==");
                break;
            }

            if (cmd.equals("article write")){
                int id = articleLastId + 1;
                articleLastId = id;
                System.out.printf("title ni kiriting => \n");
                String title = sc.nextLine();
                System.out.printf("bodyni kiriting => \n");
                String body = sc.nextLine();
                String regDate = formatter.format(now);
                Article article = new Article(id, title, body,regDate);
                articleList.add(article);
                System.out.printf("%d번 글이 생성되었습니다.\n",id);
            } else if (cmd.equals("article list")) {
                System.out.println("== 게시글 목록 == ");
                if (articleList.size() == 0){
                    System.out.println("존재하는 게시물이 없음.");
                    continue;
                }
                for (int i = articleList.size() - 1; i >= 0; i--) {
                    Article article = articleList.get(i);
                    System.out.printf("%d   |   %s  |   %s    \n", article.id, article.title, article.regDate);
                }
            } else {
                System.out.println("잘못 된 명령어 입니다.");
            }
        }
        sc.close();

    }
}

class Article {
    public int id;
    public String title;
    public String body;
    public String regDate;
    public int userId;

    public Article(int id, String title, String body, String regDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate;
    }
}

