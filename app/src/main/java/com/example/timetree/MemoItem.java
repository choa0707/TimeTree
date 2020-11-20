package com.example.timetree;

public class MemoItem {
    String title;
    String date;
    String detail;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    public MemoItem(String title, String date, String detail) {
        this.title = title;
        this.date = date;
        this.detail = detail;
    }
}
