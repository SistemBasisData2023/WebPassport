package com.WebPassport.controllers;

import com.WebPassport.entities.RequestEntity;
import com.WebPassport.models.Request;
import com.WebPassport.repositories.DocumentsRepository;
import com.WebPassport.repositories.FileRepository;
import com.WebPassport.repositories.PersonRepository;
import com.WebPassport.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    public RequestRepository requestRepository;
    public DocumentsRepository documentsRepository;
    public FileRepository fileRepository;
    public PersonRepository personRepository;

    @Autowired
    public RequestController(RequestRepository requestRepository,
                             DocumentsRepository documentsRepository,
                             FileRepository fileRepository, PersonRepository personRepository){
        this.requestRepository = requestRepository;
        this.documentsRepository = documentsRepository;
        this.fileRepository = fileRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getRequest(
            @RequestParam(required = false) Integer request_id,
            @RequestParam(required = false) Integer person_id,
            @RequestParam(required = false) Integer office_id
    ){
        try {
            List<RequestEntity> requestEntityList = new ArrayList<>();
            if(request_id != null)
                requestEntityList.addAll(requestRepository.findById(request_id));
            else if (person_id != null)
                requestEntityList.addAll(requestRepository.findByPerson_id(person_id));
            else if (office_id != null)
                requestEntityList.addAll(requestRepository.findByOffice_id(office_id));
            else
                requestEntityList.addAll(requestRepository.findAllRequest());

            if(requestEntityList.isEmpty())
                return new ResponseEntity<>(requestEntityList,HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(requestEntityList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    public Request mapToRequest(RequestEntity requestEntity){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Request(requestEntity.request_id,
                personRepository.findById(requestEntity.person_id).);
    }
     */



}
