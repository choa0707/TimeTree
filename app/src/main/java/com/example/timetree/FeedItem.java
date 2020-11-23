package com.example.timetree;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class FeedItem {
    String date;
    String title;
    Bitmap img;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public FeedItem(String date, String title, Bitmap img) {
        this.date = date;
        this.title = title;
        this.img = img;
    }


}
