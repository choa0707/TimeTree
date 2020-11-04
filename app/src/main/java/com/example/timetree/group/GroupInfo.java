package com.example.timetree.group;

public class GroupInfo {
    public String name;
    public String image;
    public String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public GroupInfo() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public GroupInfo(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
