package com.WebPassport.repositories;

import com.WebPassport.entities.OfficeEntity;

import java.util.List;

public interface OfficeRepository {
    List<OfficeEntity> findAllOffice();
    List<OfficeEntity> findById(int office_id);
    List<OfficeEntity> findByAddress_id(int address_id);
    int save(OfficeEntity officeEntity);
    int saveAndReturnId(OfficeEntity officeEntity);
    OfficeEntity saveAndReturnOffice(OfficeEntity officeEntity);
    OfficeEntity updateAndReturnOffice(int office_id, String name);
    int delete(int office_id);
}
