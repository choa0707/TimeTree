package com.example.timetree;

class GroupListItem {
    String title;
    int add = 0;

    public GroupListItem(String title, int add) {
        this.title = title;
        this.add = add;
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

