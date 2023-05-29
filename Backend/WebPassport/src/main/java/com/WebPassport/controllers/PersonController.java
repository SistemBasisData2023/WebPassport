package com.WebPassport.controllers;

import com.WebPassport.entities.PersonEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Person;
import com.WebPassport.repositories.AddressRepository;
import com.WebPassport.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    public PersonRepository personRepository;
    public AddressRepository addressRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public PersonController(PersonRepository personRepository, AddressRepository addressRepository, ObjectMapper objectMapper){
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
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
}

