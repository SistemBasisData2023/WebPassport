package com.WebPassport.services;

import com.WebPassport.entities.FileEntity;
import com.WebPassport.queries.FileQuery;
import com.WebPassport.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FileService implements FileRepository {

    public JdbcTemplate jdbcTemplate;
    @Autowired
    public FileService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.name = StringUtils.cleanPath(file.getOriginalFilename());
        fileEntity.contentType = file.getContentType();
        fileEntity.data = file.getBytes();
        fileEntity.size = file.getSize();

        return jdbcTemplate.update(FileQuery.SAVE, fileEntity.contentType,
                fileEntity.data, fileEntity.name, fileEntity.size);
    }

    @Override
    public String saveAndReturnId(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.name = StringUtils.cleanPath(file.getOriginalFilename());
        fileEntity.contentType = file.getContentType();
        fileEntity.data = file.getBytes();
        fileEntity.size = file.getSize();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(FileQuery.SAVE, new String[]{"files_id"});
            statement.setString(1, fileEntity.contentType);
            statement.setBytes(2, fileEntity.data);
            statement.setString(3, fileEntity.name);
            statement.setLong(4, fileEntity.size);
            return statement;

        }, keyHolder);

        return String.valueOf(keyHolder.getKeyList().get(0).get("files_id"));
    }

    @Override
    public List<FileEntity> findById(String files_id) {
        return jdbcTemplate.query(FileQuery.FIND_BY_ID, this::mapToFileEntity, files_id);
    }

    @Override
    public List<FileEntity> findAllFiles() {
        return jdbcTemplate.query(FileQuery.FIND_ALL, this::mapToFileEntity);
    }

    private FileEntity mapToFileEntity(ResultSet resultSet, int rowNum)
        throws SQLException{
        return new FileEntity(resultSet.getString("files_id"),
                resultSet.getString("name"),
                resultSet.getString("content_type"),
                resultSet.getLong("size"),
                resultSet.getBytes("data"));
    }
}
