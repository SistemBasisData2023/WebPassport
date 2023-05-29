package com.WebPassport.mapper;

import com.WebPassport.controllers.FileController;
import com.WebPassport.entities.*;
import com.WebPassport.models.*;
import com.WebPassport.repositories.AddressRepository;
import com.WebPassport.repositories.FileRepository;
import com.WebPassport.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IObjectMapper implements ObjectMapper{
    AddressRepository addressRepository;
    PersonRepository personRepository;
    FileRepository fileRepository;

    @Autowired
    public IObjectMapper(PersonRepository personRepository, AddressRepository addressRepository, FileRepository fileRepository){
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
    }
    @Override
    public Person mapToPerson(PersonEntity personEntity){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Person(
                    personEntity.person_id,
                    addressRepository.findById(personEntity.address_id).get(0),
                    personEntity.name, personEntity.nik,
                    sdf.parse(personEntity.date_of_birth),
                    personEntity.place_of_birth,
                    Person.Gender.valueOf(personEntity.gender)
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account mapToAccount(AccountEntity accountEntity){
        Account account =  new Account(accountEntity.account_id,
                accountEntity.username, accountEntity.email,
                accountEntity.phoneNumber, accountEntity.password);
        List<PersonEntity> personEntityList = new ArrayList<>(personRepository.findByAccount_id(account.getAccount_id()));
        List<Person> personList = new ArrayList<>();
        for(PersonEntity personEntity : personEntityList){
            personList.add(mapToPerson(personEntity));
        }
        account.persons = personList;
        return account;
    }

    @Override
    public Office mapToOffice(OfficeEntity officeEntity) {
        return new Office(officeEntity.office_id,
                addressRepository.findById(officeEntity.address_id).get(0),
                officeEntity.name);
    }

    @Override
    public Documents mapToDocuments(DocumentsEntity documentsEntity) {
        Files ktp_files = fileRepository.findById(documentsEntity.ktp_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        Files kk_files = fileRepository.findById(documentsEntity.kk_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        return new Documents(documentsEntity.document_id, ktp_files, kk_files);
    }


}
