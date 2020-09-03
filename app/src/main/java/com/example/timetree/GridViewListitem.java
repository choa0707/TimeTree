package com.example.timetree;

public class GridViewListitem {

    String title;
    String date;

    public GridViewListitem(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() { //Title 가져오는 메소드
        return title;
    }

    public String getDate() { //date 가져오는 메소드
        return date;
    }

    public void setTitle(String title) { //Title 설정하는 메소드
        this.title = title;
    }

    public void setDate(String date) { //Date 설정해주는 메소드
        this.date = date;
    }
}
