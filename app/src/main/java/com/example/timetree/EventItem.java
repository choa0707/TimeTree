package com.example.timetree;

import java.util.List;

public class EventItem {
    String title="";
    int category=0;
    int color=0;
    int alarm=0;
    int start_year, start_month, start_day, start_hour,start_minute;
    int end_year, end_month, end_day, end_hour,end_minute;


    public EventItem() {
    }

    String phone;
    String location;

    public EventItem(String title, int category, int color, int alarm, String phone, String location) {
        this.title = title;
        this.category = category;
        this.color = color;
        this.alarm = alarm;
        this.phone = phone;
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart_date(int year, int month, int date) {
        start_year = year;
        start_month = month;
        start_day = date;
    }
    public void setStart_time(int hour, int minute) {
        start_hour = hour;
        start_minute = minute;
    }
    public void setEnd_date(int year, int month, int date) {
        end_year = year;
        end_month = month;
        end_day = date;
    }
    public void setEnd_time(int hour, int minute) {
        end_hour = hour;
        end_minute = minute;
    }

    public int getStart_year() {
        return start_year;
    }

    public int getStart_month() {
        return start_month;
    }

    public int getStart_day() {
        return start_day;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public int getStart_minute() {
        return start_minute;
    }

    public int getEnd_year() {
        return end_year;
    }

    public int getEnd_month() {
        return end_month;
    }

    public int getEnd_day() {
        return end_day;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public int getEnd_minute() {
        return end_minute;
    }


    public void setCategory(int category) {
        this.category = category;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public int getCategory() {
        return category;
    }

    public int getColor() {
        return color;
    }

    public int getAlarm() {
        return alarm;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }
}
