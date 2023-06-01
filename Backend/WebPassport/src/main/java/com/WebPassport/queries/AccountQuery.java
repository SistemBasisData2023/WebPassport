package com.WebPassport.queries;

public class AccountQuery {
    public static final String FIND_ALL = "SELECT * FROM Account";
    public static final String FIND_BY_ID = "SELECT * FROM Account WHERE account_id = ?";
    public static final String FIND_BY_USERNAME = "SELECT * FROM Account WHERE username = ?";
    public static final String FIND_FOR_LOGIN = "SELECT * FROM Account WHERE (username = ? OR email = ?) AND password = ?";
    public static final String SAVE = "INSERT INTO Account"
            + " (account_id, username, email, phone_number, password)"
            + " VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String SAVE_AND_RETURN_ACCOUNT = "INSERT INTO Account"
            + " (account_id, username, email, phone_number, password)"
            + " VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *";
    public static final String UPDATE_AND_RETURN_ACCOUNT = "UPDATE Account SET " +
            "username = ?, email = ?, phone_number = ?, password = ? " +
            "WHERE account_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Account WHERE account_id = ? ";

}
