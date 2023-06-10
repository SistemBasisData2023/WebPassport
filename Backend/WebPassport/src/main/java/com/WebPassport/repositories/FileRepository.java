package com.WebPassport.repositories;

import com.WebPassport.entities.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    int save(MultipartFile file) throws IOException;
    String saveAndReturnId(MultipartFile file) throws IOException;
    FileEntity saveAndReturnFileEntity(MultipartFile file) throws IOException;
    List<FileEntity> findById(String files_id);
    List<FileEntity> findAllFiles();
    List<FileEntity> findByIdNoData(String files_id);
    List<FileEntity> findAllFilesNoData();
    FileEntity updateAndReturnFileEntity(String files_id, MultipartFile file) throws IOException;
    int delete(String files_id);
}
