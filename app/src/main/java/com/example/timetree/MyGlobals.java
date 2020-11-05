package com.example.timetree;

public class MyGlobals {
    private String groupKey="";
    private String userEmail = "";
    private String userKey="";
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }



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
