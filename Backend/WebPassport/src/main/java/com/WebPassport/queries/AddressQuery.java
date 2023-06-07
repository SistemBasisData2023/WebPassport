package com.WebPassport.queries;

public class AddressQuery {
    public static final String FIND_ALL = "SELECT * FROM Address";
    public static final String FIND_BY_ID = "SELECT * FROM Address WHERE address_id = ?";
    public static final String FIND_BY_ADDRESS_LINE = "SELECT * FROM Address WHERE address_line = ?";
    public static final String FIND_BY_SUBDISTRICT = "SELECT * FROM Address WHERE sub_district = ?";
    public static final String FIND_BY_CITY = "SELECT * FROM Address WHERE city LIKE ?";
    public static final String FIND_BY_PROVINCE = "SELECT * FROM Address WHERE province = ?";
    public static final String FIND_BY_POSTCODE = "SELECT * FROM Address WHERE postcode = ?";
    public static final String SAVE = "INSERT INTO Address"
            + " (address_id, address_line, sub_district, city, province, postcode)"
            + " VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SAVE_AND_RETURN_ADDRESS = "INSERT INTO Address"
            + " (address_id, address_line, sub_district, city, province, postcode)"
            + " VALUES (DEFAULT, ?, ?, ?, ?, ?) RETURNING *";
    public static final String UPDATE_AND_RETURN_ADDRESS = "UPDATE Address SET " +
            "address_line = ?, sub_district = ?, city = ?, province = ?, postcode = ? " +
            "WHERE address_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Address WHERE address_id = ?";
}
