package com.WebPassport.queries;

public class DocumentsQuery {
    public static final String FIND_ALL = "SELECT * FROM Documents";
    public static final String FIND_BY_ID = "SELECT * FROM Documents WHERE document_id = ?";
    public static final String SAVE = "INSERT INTO Documents VALUES " +
            "(DEFAULT, ?::uuid, ?::uuid)";
}
