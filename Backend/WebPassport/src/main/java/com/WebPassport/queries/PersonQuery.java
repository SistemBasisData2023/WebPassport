package com.WebPassport.queries;

public class PersonQuery {
    public static final String FIND_ALL = "SELECT person_id, account_id, address_id, name, nik, " +
            "to_char(date_of_birth, 'YYYY-MM-DD') as date_of_birth, place_of_birth, gender FROM Person";
    public static final String FIND_BY_ID = "SELECT person_id, account_id, address_id, name, nik, " +
            "to_char(date_of_birth, 'YYYY-MM-DD') as date_of_birth, place_of_birth, gender FROM Person WHERE person_id = ?";
    public static final String FIND_BY_ACCOUNT_ID = "SELECT person_id, account_id, address_id, name, " +
            "nik, to_char(date_of_birth, 'YYYY-MM-DD') as date_of_birth, place_of_birth, gender FROM Person WHERE account_id = ?";
    public static final String FIND_BY_ADDRESS_ID = "SELECT person_id, account_id, address_id, name, nik, " +
            "to_char(date_of_birth, 'YYYY-MM-DD') as date_of_birth, place_of_birth, gender FROM Person WHERE address_id = ?";
    public static final String FIND_BY_REQUEST_ID = " SELECT * FROM Person WHERE person_id IN " +
            "(SELECT person_id FROM REQUEST WHERE request_id = ?)";
    public static final String SAVE = "INSERT INTO Person "
            + " (person_id, account_id, address_id, name, nik, date_of_birth, place_of_birth, gender)"
            + " VALUES (DEFAULT, ?, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), ?, ?::gender)";
    public static final String UPDATE_AND_RETURN_PERSON = "UPDATE Person SET " +
            "name = ?, nik = ?, date_of_birth = to_date(?, 'YYYY-MM-DD'), place_of_birth = ?, gender = ?::gender " +
            "WHERE person_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Person WHERE person_id = ?";
    public static final String DELETE_BY_ACCOUNT_ID = "DELETE FROM Person WHERE account_id = ?";
}
