package com.WebPassport.repositories;

import com.WebPassport.entities.RequestEntity;

import java.util.List;

public interface RequestRepository {
    List<RequestEntity> findAllRequest();
    List<RequestEntity> findById(int request_id);
    List<RequestEntity> findByDocument_id(int document_id);
    List<RequestEntity> findByOffice_id(int office_id);
    List<RequestEntity> findByPerson_id(int person_id);
    int save(RequestEntity requestEntity);
    int saveAndReturnId(RequestEntity requestEntity);
    RequestEntity saveAndReturnRequestEntity(RequestEntity requestEntity);
    RequestEntity updateAndReturnRequestEntity(int request_id, String schedule, String status);
    int delete(int request_id);
}
