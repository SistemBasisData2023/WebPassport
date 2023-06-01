package com.WebPassport.repositories;

import com.WebPassport.entities.PersonEntity;

import java.util.List;

public interface PersonRepository {
    List<PersonEntity> findAllPerson();
    List<PersonEntity> findById(int person_id);
    List<PersonEntity> findByAccount_id(int account_id);
    List<PersonEntity> findByAddress_id(int address_id);
    int save(PersonEntity personEntity);
    PersonEntity updateAndReturnPersonEntity(int person_id, PersonEntity personEntity);
    int delete(int person_id);
    int deleteByAccount_id(int account_id);
}
