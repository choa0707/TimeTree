package com.example.timetree.group;

class GroupListItem {
    String title;
    int add = 0;
    String image_url;
    String key;


    public String getKey() {
        return key;
    }
    public GroupListItem(String title, int add, String image_url) {
        this.title = title;
        this.add = add;
        this.image_url = image_url;
    }
    public GroupListItem(String title, int add, String image_url, String key) {
        this.title = title;
        this.add = add;
        this.image_url = image_url;
        this.key = key;
    }
    public String getImage_url() {
        return image_url;
    }
    public String getTitle() {
        return title;
    }

    public int getAdd() {
        return add;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
