package com.WebPassport.repositories;

import com.WebPassport.entities.DocumentsEntity;

import java.util.List;

public interface DocumentsRepository {
    int save(DocumentsEntity documentsEntity);
    int saveAndReturnId(DocumentsEntity documentsEntity);
    DocumentsEntity saveAndReturnDocuments(DocumentsEntity documentsEntity);
    List<DocumentsEntity> findAllDocuments();
    List<DocumentsEntity> findByDocument_id(int document_id);
    int delete(int document_id);

}
