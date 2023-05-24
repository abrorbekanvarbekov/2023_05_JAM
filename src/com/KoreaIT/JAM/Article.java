package com.KoreaIT.JAM;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Article {
    public int id;
    public String title;
    public String body;
    public LocalDateTime regDate;
    public LocalDateTime updateDate;

//    public Article(int id, String title, String body, String regDate, String updateDate) {
//        this.id = id;
//        this.title = title;
//        this.body = body;
//        this.regDate = regDate;
//        this.updateDate = updateDate;
//    }

    public Article(Map<String, Object> articleMap) {
        this.id = (int) articleMap.get("id");
        this.title = (String) articleMap.get("title");
        this.body = (String) articleMap.get("body");
        this.regDate = (LocalDateTime) articleMap.get("regDate");
        this.updateDate = (LocalDateTime) articleMap.get("updateDate");

    }
}
