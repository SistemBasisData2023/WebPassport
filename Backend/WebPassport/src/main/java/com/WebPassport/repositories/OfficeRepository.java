package com.WebPassport.repositories;

import com.WebPassport.entities.OfficeEntity;

import java.util.List;

public interface OfficeRepository {
    List<OfficeEntity> findAllOffice();
    List<OfficeEntity> findById(int office_id);
    List<OfficeEntity> findByAddress_id(int address_id);
    int save(OfficeEntity officeEntity);
}
