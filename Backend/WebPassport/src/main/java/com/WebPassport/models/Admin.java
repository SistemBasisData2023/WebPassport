package com.WebPassport.models;

public class Admin {
    private int admin_id;
    public String username;
    public String email;
    public String phoneNumber;
    public String password;

    public Admin(){

    }
    public Admin(int admin_id, String username, String email, String phoneNumber, String password) {
        this.admin_id = admin_id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Admin(String username, String email, String phoneNumber, String password) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
}
