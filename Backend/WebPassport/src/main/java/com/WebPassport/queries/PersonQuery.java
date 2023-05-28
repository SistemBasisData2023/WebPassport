package com.WebPassport.queries;

public class PersonQuery {
    public static final String FIND_ALL = "SELECT * FROM Person";
    public static final String FIND_BY_ID = "SELECT * FROM Person WHERE person_id = ?";
    public static final String FIND_BY_ACCOUNT_ID = "SELECT * FROM Person WHERE account_id = ?";
    public static final String FIND_BY_ADDRESS_ID = "SELECT * FROM Person WHERE address_id = ?";
    public static final String SAVE = "INSERT INTO Person "
            + " (person_id, account_id, address_id, name, nik, date_of_birth, place_of_birth, gender)"
            + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
}
