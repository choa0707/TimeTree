package com.example.timetree;

public class MemoItem {
    String title;
    String date;
    String detail;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    String key;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MemoItem() {
    }

    public MemoItem(String title, String date, String detail) {
        this.title = title;
        this.date = date;
        this.detail = detail;
    }
}
