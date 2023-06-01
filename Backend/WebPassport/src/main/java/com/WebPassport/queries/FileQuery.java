package com.WebPassport.queries;

public class FileQuery {
    public static final String FIND_ALL = "SELECT * FROM Files";
    public static final String FIND_BY_ID = "SELECT * FROM Files WHERE files_id = ?::uuid";
    public static final String SAVE = "INSERT INTO Files (files_id, content_type," +
            "data, name, size) VALUES " +
            "(DEFAULT, ?, ?, ?, ?)";
    public static final String SAVE_AND_RETURN_FILE = "INSERT INTO Files (files_id, content_type," +
            "data, name, size) VALUES " +
            "(DEFAULT, ?, ?, ?, ?) RETURNING *";
    public static final String UPDATE_AND_RETURN_FILE = "UPDATE Files SET " +
            "content_type = ?, data = ?, name = ?, size = ? " +
            "WHERE files_id = ?::uuid RETURNING *";
    public static final String DELETE = "DELETE FROM Files WHERE files_id = ?::uuid";
}
