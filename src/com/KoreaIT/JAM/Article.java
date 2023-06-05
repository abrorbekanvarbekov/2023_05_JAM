package com.KoreaIT.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
    public int id;
    public String title;
    public String body;
    public String writer;
    public int memberId;
    public LocalDateTime regDate;
    public LocalDateTime updateDate;
    public int views;

    public Article(Map<String, Object> articleMap) {
        this.id = (int) articleMap.get("id");
        this.title = (String) articleMap.get("title");
        this.body = (String) articleMap.get("body");
        this.writer = (String) articleMap.get("name");
        this.memberId = (int) articleMap.get("memberId");
        this.regDate = (LocalDateTime) articleMap.get("regDate");
        this.updateDate = (LocalDateTime) articleMap.get("updateDate");
        this.views = (int) articleMap.get("views");
    }

    public void getInfo() {
        System.out.printf("번호  :  %s\n", this.id);
        System.out.printf("작성날짜  :  %s\n", this.regDate);
        System.out.printf("수정날짜  :  %s\n", this.updateDate);
        System.out.printf("제목  :  %s\n", this.title);
        System.out.printf("내용  :  %s\n", this.body);
        System.out.printf("작성자  :  %s\n", this.writer);
        System.out.printf("조회수  :  %d\n", this.views);
    }
}
