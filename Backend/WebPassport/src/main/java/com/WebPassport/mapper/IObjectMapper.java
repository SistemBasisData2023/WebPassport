package com.WebPassport.mapper;

import com.WebPassport.controllers.FileController;
import com.WebPassport.entities.*;
import com.WebPassport.models.*;
import com.WebPassport.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Repository
public class IObjectMapper implements ObjectMapper{
    AddressRepository addressRepository;
    PersonRepository personRepository;
    FileRepository fileRepository;
    DocumentsRepository documentsRepository;
    RequestRepository requestRepository;

    @Autowired
    public IObjectMapper(PersonRepository personRepository, AddressRepository addressRepository,
                         FileRepository fileRepository, DocumentsRepository documentsRepository, RequestRepository requestRepository){
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
        this.documentsRepository = documentsRepository;
        this.requestRepository = requestRepository;
    }
    @Override
    public Person mapToPerson(PersonEntity personEntity){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Person person =  new Person(
                    personEntity.person_id,
                    addressRepository.findById(personEntity.address_id).get(0),
                    personEntity.name, personEntity.nik,
                    sdf.parse(personEntity.date_of_birth),
                    personEntity.place_of_birth,
                    Person.Gender.valueOf(personEntity.gender)
            );
            List<RequestEntity> requestEntityList = new ArrayList<>(requestRepository.findByPerson_id(personEntity.person_id));
            List<Request> requestList = new ArrayList<>();
            for (RequestEntity requestEntity : requestEntityList){
                requestList.add(mapToRequest(requestEntity));
            }
            person.requests = requestList;
            return person;
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
        Office office = new Office(officeEntity.office_id,
                addressRepository.findById(officeEntity.address_id).get(0),
                officeEntity.name);
        List<RequestEntity> requestEntityList = new ArrayList<>(requestRepository.findByOffice_id(officeEntity.office_id));
        List<Request> requestList = new ArrayList<>();
        for (RequestEntity requestEntity : requestEntityList){
            try {
                requestList.add(mapToRequest(requestEntity));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        office.requests = requestList;
        return office;
    }

    @Override
    public Documents mapToDocuments(DocumentsEntity documentsEntity) {
        Files ktp_files = fileRepository.findById(documentsEntity.ktp_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        Files kk_files = fileRepository.findById(documentsEntity.kk_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        return new Documents(documentsEntity.document_id, ktp_files, kk_files);
    }

    @Override
    public Request mapToRequest(RequestEntity requestEntity) throws ParseException {
        Documents documents = mapToDocuments(documentsRepository.findByDocument_id(requestEntity.document_id).get(0));
        SimpleDateFormat timestampSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestampSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new Request(requestEntity.request_id, documents,
                timestampSDF.parse(requestEntity.timestamp),
                timestampSDF.parse(requestEntity.schedule),
                Request.Status.valueOf(requestEntity.status));
    }
}
