package com.WebPassport.controllers;

import com.WebPassport.entities.FileEntity;
import com.WebPassport.models.Files;
import com.WebPassport.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {
    public FileRepository fileRepository;

    @Autowired
    public FileController(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getFiles(@RequestParam(required = false) String files_id){
        List<Files> filesList;
        if(files_id != null)
            filesList = fileRepository.findById(files_id).stream().map(FileController::mapToFiles).collect(Collectors.toList());
        else
            filesList = fileRepository.findAllFiles().stream().map(FileController::mapToFiles).collect(Collectors.toList());
        return new ResponseEntity<>(filesList, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            fileRepository.save(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{files_id}")
    public ResponseEntity<?> getFile(@PathVariable String files_id){
        List<FileEntity> fileEntities = fileRepository.findById(files_id);
        if (fileEntities.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        FileEntity fileEntity = fileEntities.get(0);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.name+ "\"")
                .contentType(MediaType.valueOf(fileEntity.contentType))
                .body(fileEntity.data);
    }

    public static Files mapToFiles(FileEntity fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getFiles_id())
                .toUriString();
        Files files = new Files();
        files.files_id = fileEntity.getFiles_id();
        files.name = fileEntity.name;
        files.contentType = fileEntity.contentType;
        files.size = fileEntity.size;
        files.url = downloadURL;

        return files;
    }
}
