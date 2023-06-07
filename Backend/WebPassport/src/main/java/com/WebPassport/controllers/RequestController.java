package com.WebPassport.controllers;

import com.WebPassport.entities.RequestEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Request;
import com.WebPassport.repositories.DocumentsRepository;
import com.WebPassport.repositories.FileRepository;
import com.WebPassport.repositories.PersonRepository;
import com.WebPassport.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RequestController {

    public RequestRepository requestRepository;
    public DocumentsRepository documentsRepository;
    public FileRepository fileRepository;
    public PersonRepository personRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public RequestController(RequestRepository requestRepository,
                             DocumentsRepository documentsRepository,
                             FileRepository fileRepository, PersonRepository personRepository,
                             ObjectMapper objectMapper){
        this.requestRepository = requestRepository;
        this.documentsRepository = documentsRepository;
        this.fileRepository = fileRepository;
        this.personRepository = personRepository;
        this.objectMapper = objectMapper;
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
            List<Request> requestList = new ArrayList<>();
            for (RequestEntity requestEntity : requestEntityList)
                requestList.add(objectMapper.mapToRequest(requestEntity));

            return new ResponseEntity<>(requestList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{request_id}/update")
    public ResponseEntity<?> updateRequest(
            @PathVariable int request_id,
            @RequestParam(required = false) String schedule,
            @RequestParam(required = false) String status){
        try{
            List<RequestEntity> requestEntityList = requestRepository.findById(request_id);
            if(requestEntityList.isEmpty()){
                return new ResponseEntity<>("Request Not Found", HttpStatus.NO_CONTENT);
            }
            RequestEntity currentRequest = requestEntityList.get(0);
            RequestEntity requestEntityUpdate = requestRepository
                    .updateAndReturnRequestEntity(
                            request_id,
                            schedule == null ? currentRequest.schedule : schedule,
                            status == null ? currentRequest.status : status);
            Request requestUpdate = objectMapper.mapToRequest(requestEntityUpdate);
            return new ResponseEntity<>(requestUpdate, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
