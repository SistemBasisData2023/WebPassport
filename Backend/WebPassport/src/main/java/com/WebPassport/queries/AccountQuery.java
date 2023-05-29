package com.WebPassport.queries;

public class AccountQuery {
    public static final String FIND_ALL = "SELECT * FROM Account";
    public static final String FIND_BY_ID = "SELECT * FROM Account WHERE account_id = ?";
    public static final String FIND_BY_USERNAME = "SELECT * FROM Account WHERE username = ?";
    public static final String FIND_FOR_LOGIN = "SELECT * FROM Account WHERE (username = ? OR email = ?) AND password = ?";
    public static final String SAVE = "INSERT INTO Account"
            + " (account_id, username, email, phone_number, password)"
            + " VALUES (DEFAULT, ?, ?, ?, ?)";
}
