package com.WebPassport.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private int account_id;
    public String username;
    public String email;
    public String phoneNumber;
    public String password;
    public List<Person> persons = new ArrayList<>();

    public static final String REGEX_EMAIL = "[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
    public static final String REGEX_PHONE = "[0-9]{8,13}";
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$";

    public Account(String username, String email, String phoneNumber, String password){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Account(int account_id, String username, String email, String phoneNumber, String password){
        this.account_id = account_id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static boolean validate(String email, String phoneNumber, String password){
        Pattern emailPattern = Pattern.compile(REGEX_EMAIL);
        Pattern phoneNumberPattern = Pattern.compile(REGEX_PHONE);
        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);

        Matcher emailMatcher = emailPattern.matcher(email);
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phoneNumber);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        return (emailMatcher.find() || phoneNumberMatcher.find()) && passwordMatcher.find();
    }
    public int getAccount_id() {return account_id;}
    public void setAccount_id(int id) {this.account_id = id;}

    public void addPerson(Person person){
        persons.add(person);
    }
}
