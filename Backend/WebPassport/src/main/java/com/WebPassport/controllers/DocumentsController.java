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
@CrossOrigin
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
            @RequestParam("ktp_files") MultipartFile ktp_files,
            @RequestParam("kk_files") MultipartFile kk_files){
        try {
            String ktp_files_id = fileRepository.saveAndReturnId(ktp_files);
            String kk_files_id = fileRepository.saveAndReturnId(kk_files);

            DocumentsEntity documentsEntity = documentsRepository.saveAndReturnDocuments(new DocumentsEntity(ktp_files_id, kk_files_id));
            Documents documents = objectMapper.mapToDocuments(documentsEntity);
            return new ResponseEntity<>(documents, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{document_id}/delete")
    public ResponseEntity<?> deleteDocuments(@PathVariable int document_id){
        try{
            List<DocumentsEntity> documentsEntities = new ArrayList<>(documentsRepository.findByDocument_id(document_id));
            if (documentsEntities.isEmpty()){
                return new ResponseEntity<>("Documents Not Found", HttpStatus.NO_CONTENT);
            }
            DocumentsEntity documentsEntity = documentsEntities.get(0);
            fileRepository.delete(documentsEntity.ktp_files_id);
            fileRepository.delete(documentsEntity.kk_files_id);
            documentsRepository.delete(document_id);
            return new ResponseEntity<>("Delete document with files ID " + documentsEntity.ktp_files_id +
                    " and " + documentsEntity.kk_files_id, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
