package com.KoreaIT.JAM;

public class Article {
    public int id;
    public String title;
    public String body;
    public String regDate;
    public String updateDate;

    public Article(int id, String title, String body, String regDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }
}
