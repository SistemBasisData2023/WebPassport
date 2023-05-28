package com.WebPassport.entities;

public class AccountEntity {
    public int account_id;
    public String username;
    public String email;
    public String phoneNumber;
    public String password;

    public AccountEntity(){

    }

    public AccountEntity(int account_id, String username, String email, String phoneNumber, String password){
        this.account_id = account_id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public AccountEntity(String username, String email, String phoneNumber, String password){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
