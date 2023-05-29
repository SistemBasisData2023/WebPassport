package com.WebPassport.controllers;

import com.WebPassport.entities.DocumentsEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Documents;
import com.WebPassport.repositories.DocumentsRepository;
import com.WebPassport.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentsController {
    public DocumentsRepository documentsRepository;
    public FileRepository fileRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public DocumentsController(DocumentsRepository documentsRepository,
                               FileRepository fileRepository, ObjectMapper objectMapper){
        this.documentsRepository = documentsRepository;
        this.fileRepository = fileRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getDocuments(
            @RequestParam(required = false) Integer document_id
    ){
        try{
            List<DocumentsEntity> documentsEntities = new ArrayList<>();
            if(document_id != null)
                documentsEntities.addAll(documentsRepository.findByDocument_id(document_id));
            else
                documentsEntities.addAll(documentsRepository.findAllDocuments());
            if(documentsEntities.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<Documents> documentsList = new ArrayList<>();
            for (DocumentsEntity documentsEntity : documentsEntities){
                documentsList.add(objectMapper.mapToDocuments(documentsEntity));
            }
            return new ResponseEntity<>(documentsList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDocuments(
            @RequestParam MultipartFile ktp_files,
            @RequestParam MultipartFile kk_files){
        try {
            String ktp_files_id = fileRepository.saveAndReturnId(ktp_files);
            String kk_files_id = fileRepository.saveAndReturnId(kk_files);

            int document_id = documentsRepository.saveAndReturnId(new DocumentsEntity(ktp_files_id, kk_files_id));
            return new ResponseEntity<>(document_id, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
