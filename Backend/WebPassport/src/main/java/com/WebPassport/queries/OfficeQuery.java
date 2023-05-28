package com.WebPassport.queries;

public class OfficeQuery {
    public static final String FIND_ALL = "SELECT * FROM Office";
    public static final String FIND_BY_ID = "SELECT * FROM Office WHERE office_id = ?";
    public static final String FIND_BY_ADDRESS_ID = "SELECT * FROM Office WHERE address_id = ?";
    public static final String SAVE ="INSERT INTO Office " +
            "VALUES (DEFAULT, ?, ?)";
}
