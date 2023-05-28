package com.WebPassport.repositories;

import com.WebPassport.entities.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    int save(MultipartFile file) throws IOException;
    List<FileEntity> findById(String files_id);
    List<FileEntity> findAllFiles();
}
