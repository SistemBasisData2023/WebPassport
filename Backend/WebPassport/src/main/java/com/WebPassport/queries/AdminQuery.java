package com.WebPassport.queries;

public class AdminQuery {
    public static final String FIND_ALL = "SELECT * FROM Admin";
    public static final String FIND_BY_ID = "SELECT * FROM Admin WHERE Admin_id = ?";
    public static final String FIND_BY_USERNAME = "SELECT * FROM Admin WHERE username = ?";
    public static final String FIND_FOR_LOGIN = "SELECT * FROM Admin WHERE (username = ? OR email = ?) AND password = ?";
    public static final String SAVE = "INSERT INTO Admin"
            + " (Admin_id, username, email, phone_number, password)"
            + " VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String SAVE_AND_RETURN_ADMIN = "INSERT INTO Admin"
            + " (Admin_id, username, email, phone_number, password)"
            + " VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *";
    public static final String UPDATE_AND_RETURN_ADMIN = "UPDATE Admin SET " +
            "username = ?, email = ?, phone_number = ?, password = ? " +
            "WHERE Admin_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Admin WHERE Admin_id = ? ";
}
