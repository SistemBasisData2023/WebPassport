package com.WebPassport.controllers;

import com.WebPassport.entities.DocumentsEntity;
import com.WebPassport.entities.PersonEntity;
import com.WebPassport.entities.RequestEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Person;
import com.WebPassport.models.Request;
import com.WebPassport.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/person")
public class PersonController {
    public PersonRepository personRepository;
    public AddressRepository addressRepository;
    public OfficeRepository officeRepository;
    public FileRepository fileRepository;
    public DocumentsRepository documentsRepository;
    public RequestRepository requestRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public PersonController(PersonRepository personRepository, AddressRepository addressRepository, OfficeRepository officeRepository,
                            FileRepository fileRepository, DocumentsRepository documentsRepository, RequestRepository requestRepository,
                            ObjectMapper objectMapper){
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.officeRepository = officeRepository;
        this.fileRepository = fileRepository;
        this.documentsRepository = documentsRepository;
        this.objectMapper = objectMapper;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPersons(
            @RequestParam(required = false) Integer person_id,
            @RequestParam(required = false) Integer account_id,
            @RequestParam(required = false) Integer address_id
    ){
        try {
            List<PersonEntity> personEntityList = new ArrayList<>();
            if (person_id != null)
                personEntityList.addAll(personRepository.findById(person_id));
            else if(account_id != null)
                personEntityList.addAll(personRepository.findByAccount_id(account_id));
            else if(address_id != null)
                personEntityList.addAll(personRepository.findByAddress_id(address_id));
            else
                personEntityList.addAll(personRepository.findAllPerson());

            if(personEntityList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Person> personList = new ArrayList<>();
            for (PersonEntity personEntity: personEntityList){
                personList.add(objectMapper.mapToPerson(personEntity));
            }
            return new ResponseEntity<>(personList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{person_id}/createrequest")
    public ResponseEntity<?> createRequest(
            @PathVariable int person_id, @RequestParam int office_id, @RequestParam String schedule,
            @RequestParam MultipartFile ktp_files,
            @RequestParam MultipartFile kk_files){
        try{
            if(officeRepository.findById(office_id).isEmpty()){
                return new ResponseEntity<>("Office Not Found", HttpStatus.NOT_FOUND);
            }
            String ktp_files_id = fileRepository.saveAndReturnId(ktp_files);
            String kk_files_id = fileRepository.saveAndReturnId(kk_files);
            int document_id = documentsRepository.saveAndReturnId(new DocumentsEntity(ktp_files_id, kk_files_id));
            RequestEntity requestEntity = requestRepository.saveAndReturnRequestEntity(
                    new RequestEntity(document_id, office_id, person_id, schedule, Request.Status.PENDING.toString())
            );
            Request request = objectMapper.mapToRequest(requestEntity);

            return new ResponseEntity<>(request, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{person_id}/update")
    public ResponseEntity<?> updatePerson(
            @PathVariable int person_id, @RequestParam(required = false) String name,
            @RequestParam(required = false) String nik, @RequestParam(required = false) String date_of_birth,
            @RequestParam(required = false) String place_of_birth, @RequestParam(required = false) String gender){
        try {
            List<PersonEntity> personEntityList = personRepository.findById(person_id);
            if(personEntityList.isEmpty()){
                return new ResponseEntity<>("Person Not Found", HttpStatus.NOT_FOUND);
            }
            PersonEntity currentPerson = personEntityList.get(0);
            PersonEntity personEntity = new PersonEntity(
                    (name == null ? currentPerson.name : name),
                    (nik == null ? currentPerson.nik : nik),
                    (date_of_birth == null ? currentPerson.date_of_birth : date_of_birth),
                    (place_of_birth == null ? currentPerson.place_of_birth : place_of_birth),
                    (gender == null ? currentPerson.gender : gender));
            PersonEntity personEntityUpdate = personRepository.updateAndReturnPersonEntity(person_id, personEntity);
            return new ResponseEntity<>(personEntityUpdate, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

