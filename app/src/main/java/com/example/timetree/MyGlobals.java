package com.example.timetree;

public class MyGlobals {
    private String groupKey="";


    public void setgroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
    public String getgroupKey() {
        return groupKey;
    }
    private static MyGlobals instance = null;
    public static synchronized MyGlobals getInstance(){
        if(null == instance){
            instance = new MyGlobals();
        }
        return instance;
    }
}
