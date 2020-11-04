package com.example.timetree;

public class MyGlobals {
    private String mGlobalString="";

    public void setmGlobalString(String mGlobalString) {
        this.mGlobalString = mGlobalString;
    }
    public String getmGlobalString() {
        return mGlobalString;
    }
    private static MyGlobals instance = null;
    public static synchronized MyGlobals getInstance(){
        if(null == instance){
            instance = new MyGlobals();
        }
        return instance;
    }
}
