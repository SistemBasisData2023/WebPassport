package com.WebPassport.queries;

public class RequestQuery {
    public static final String FIND_ALL = "SELECT * FROM Request";
    public static final String FIND_BY_ID = "SELECT * FROM Request WHERE request_id = ?";
    public static final String FIND_BY_DOCUMENT_ID = "SELECT * FROM Request WHERE document_id = ?";
    public static final String FIND_BY_OFFICE_ID = "SELECT * FROM Request WHERE office_id = ?";
    public static final String FIND_BY_PERSON_ID = "SELECT * FROM Request WHERE person_id = ?";
    public static final String SAVE = "INSERT INTO Request " +
            "(request_id, document_id, office_id, person_id, schedule, timestamp) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT)";
}
