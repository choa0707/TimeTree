package com.example.timetree;

public class UserData {
    String email;
    String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserData(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
