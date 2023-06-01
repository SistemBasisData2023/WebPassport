package com.WebPassport.queries;

public class RequestQuery {
    public static final String FIND_ALL = "SELECT * FROM Request";
    public static final String FIND_BY_ID = "SELECT * FROM Request WHERE request_id = ?";
    public static final String FIND_BY_DOCUMENT_ID = "SELECT * FROM Request WHERE document_id = ?";
    public static final String FIND_BY_OFFICE_ID = "SELECT * FROM Request WHERE office_id = ?";
    public static final String FIND_BY_PERSON_ID = "SELECT * FROM Request WHERE person_id = ?";
    public static final String SAVE = "INSERT INTO Request " +
            "(request_id, document_id, office_id, person_id, schedule, timestamp, status) " +
            "VALUES (DEFAULT, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), DEFAULT, ?::status)";
    public static final String SAVE_AND_RETURN_REQUEST_ENTITY = "INSERT INTO Request " +
            "(request_id, document_id, office_id, person_id, schedule, timestamp, status) " +
            "VALUES (DEFAULT, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), DEFAULT, ?::status) RETURNING *";
    public static final String UPDATE_AND_RETURN_REQUEST_ENTITY = "UPDATE Request SET " +
            "schedule = to_date(?, 'YYYY-MM-DD'), status = ?::status WHERE request_id = ? RETURNING *";
    public static final String DELETE = "DELETE FROM Request WHERE request_id = ?";
}
