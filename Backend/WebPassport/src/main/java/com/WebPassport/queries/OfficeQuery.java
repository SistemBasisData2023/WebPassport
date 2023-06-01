package com.WebPassport.queries;

public class OfficeQuery {
    public static final String FIND_ALL = "SELECT * FROM Office";
    public static final String FIND_BY_ID = "SELECT * FROM Office WHERE office_id = ?";
    public static final String FIND_BY_ADDRESS_ID = "SELECT * FROM Office WHERE address_id = ?";
    public static final String SAVE = "INSERT INTO Office " +
            "VALUES (DEFAULT, ?, ?)";
    public static final String SAVE_AND_RETURN_OFFICE = "INSERT INTO Office " +
            "VALUES (DEFAULT, ?, ?) RETURNING *";
    public static final String UPDATE_AND_RETURN_OFFICE = "UPDATE Office SET " +
            "name = ? WHERE office_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Office WHERE office_id = ?";
}
