package com.example.gymtopia;

public class Admin {
    private static Admin instance;
    public String username, password;

    private Admin(String username , String password) {
        this.username = username;
        this.password = password;
    }

    public synchronized static Admin getInstance(String username , String password) {
        if (instance == null) {
            instance = new Admin(username , password);
        }
        return instance;
    }
}
