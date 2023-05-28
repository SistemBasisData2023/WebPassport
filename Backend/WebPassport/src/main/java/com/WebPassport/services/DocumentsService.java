package com.WebPassport.services;

import com.WebPassport.entities.DocumentsEntity;
import com.WebPassport.queries.DocumentsQuery;
import com.WebPassport.repositories.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public List<DocumentsEntity> findAllDocuments() {
        return jdbcTemplate.query(DocumentsQuery.FIND_ALL, this::mapToDocumentsEntity);
    }

    @Override
    public List<DocumentsEntity> findByDocument_id(int document_id) {
        return jdbcTemplate.query(DocumentsQuery.FIND_BY_ID, this::mapToDocumentsEntity, document_id);
    }

    public DocumentsEntity mapToDocumentsEntity(ResultSet resultSet, int rowNum)
        throws SQLException{
        return new DocumentsEntity(resultSet.getInt("document_id"),
                resultSet.getString("ktp_files_id"),
                resultSet.getString("kk_files_id"));
    }
}
