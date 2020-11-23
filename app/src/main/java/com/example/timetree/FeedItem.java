package com.example.timetree;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class FeedItem {
    String s_date;
    String e_date;
    String title;
    Bitmap img;
    long comp;

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
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

    public long getComp() {
        return comp;
    }

    public void setComp(long comp) {
        this.comp = comp;
    }

    public FeedItem(String s_date, String e_date, String title, Bitmap img, long comp) {
        this.s_date = s_date;
        this.e_date = e_date;
        this.title = title;
        this.img = img;
        this.comp = comp;
    }
}
