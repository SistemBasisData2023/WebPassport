package com.WebPassport.queries;

public class DocumentsQuery {
    public static final String FIND_ALL = "SELECT * FROM Documents";
    public static final String FIND_BY_ID = "SELECT * FROM Documents WHERE document_id = ?";
    public static final String SAVE = "INSERT INTO Documents " +
            "(document_id, ktp_files_id, kk_files_id) VALUES " +
            "(DEFAULT, ?::uuid, ?::uuid)";
    public static final String SAVE_AND_RETURN_DOCUMENTS = "INSERT INTO Documents " +
            "(document_id, ktp_files_id, kk_files_id) VALUES " +
            "(DEFAULT, ?::uuid, ?::uuid) RETURNING *";
    public static final String DELETE = "DELETE FROM Documents WHERE document_id = ?";

}
