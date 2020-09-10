package com.example.timetree;

import java.util.List;

public class EventItem {
    String title;
    String start_date;
    String end_date;
    List<String> category;
    Integer color;
    Integer alarm;
    String phone;
    String location;

    public EventItem(String title, String start_date, String end_date, List<String> category, Integer color, Integer alarm, String phone, String location) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.category = category;
        this.color = color;
        this.alarm = alarm;
        this.phone = phone;
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public void setAlarm(Integer alarm) {
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

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public List<String> getCategory() {
        return category;
    }

    public Integer getColor() {
        return color;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }
}
