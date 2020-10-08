package com.example.timetree;

class GroupListItem {
    String title;
    int add = 0;
    String group_id;

    public GroupListItem(String title, int add, String group_id) {
        this.title = title;
        this.add = add;
        this.group_id = group_id;
    }
    public String getGroup_id() {
        return group_id;
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

