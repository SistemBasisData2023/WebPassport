package com.WebPassport.controllers;

import com.WebPassport.entities.DocumentsEntity;
import com.WebPassport.models.Documents;
import com.WebPassport.models.Files;
import com.WebPassport.repositories.DocumentsRepository;
import com.WebPassport.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentsController {
    public DocumentsRepository documentsRepository;
    public FileRepository fileRepository;

    @Autowired
    public DocumentsController(DocumentsRepository documentsRepository, FileRepository fileRepository){
        this.documentsRepository = documentsRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping
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
                documentsList.add(mapToDocuments(documentsEntity));
            }
            return new ResponseEntity<>(documentsList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Documents mapToDocuments(DocumentsEntity documentsEntity){
        Files ktp_files = fileRepository.findById(documentsEntity.ktp_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        Files kk_files = fileRepository.findById(documentsEntity.kk_files_id).stream().map(FileController::mapToFiles).toList().get(0);
        return new Documents(documentsEntity.document_id, ktp_files, kk_files);
    }

}
