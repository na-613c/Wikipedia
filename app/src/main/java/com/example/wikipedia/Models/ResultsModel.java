package com.example.wikipedia.Models;

public class ResultsModel {
    private String title;
    private int pageid;
    private String body;

    public ResultsModel(String title, int pageid, String body) {
        this.title = title;
        this.pageid = pageid;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResultsModel{" +
                "title='" + title + '\'' +
                ", pageid=" + pageid +
                ", body='" + body + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
