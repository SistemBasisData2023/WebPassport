package com.WebPassport.services;

import com.WebPassport.entities.DocumentsEntity;
import com.WebPassport.entities.OfficeEntity;
import com.WebPassport.queries.DocumentsQuery;
import com.WebPassport.queries.OfficeQuery;
import com.WebPassport.repositories.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DocumentsService implements DocumentsRepository {
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public DocumentsService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(DocumentsEntity documentsEntity) {
        return jdbcTemplate.update(DocumentsQuery.SAVE,
                documentsEntity.ktp_files_id,
                documentsEntity.kk_files_id);
    }

    @Override
    public int saveAndReturnId(DocumentsEntity documentsEntity){
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(DocumentsQuery.SAVE, new String[]{"document_id"});
            statement.setString(1, documentsEntity.ktp_files_id);
            statement.setString(2, documentsEntity.kk_files_id);
            return statement;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public DocumentsEntity saveAndReturnDocuments(DocumentsEntity documentsEntity) {
        return jdbcTemplate.query(DocumentsQuery.SAVE_AND_RETURN_DOCUMENTS, this::mapToDocumentsEntity,
                documentsEntity.ktp_files_id, documentsEntity.kk_files_id).get(0);
    }

    @Override
    public List<DocumentsEntity> findAllDocuments() {
        return jdbcTemplate.query(DocumentsQuery.FIND_ALL, this::mapToDocumentsEntity);
    }

    @Override
    public List<DocumentsEntity> findByDocument_id(int document_id) {
        return jdbcTemplate.query(DocumentsQuery.FIND_BY_ID, this::mapToDocumentsEntity, document_id);
    }

    @Override
    public int delete(int document_id) {
        return jdbcTemplate.update(DocumentsQuery.DELETE, document_id);
    }

    public DocumentsEntity mapToDocumentsEntity(ResultSet resultSet, int rowNum)
        throws SQLException{
        return new DocumentsEntity(resultSet.getInt("document_id"),
                resultSet.getString("ktp_files_id"),
                resultSet.getString("kk_files_id"));
    }
}
