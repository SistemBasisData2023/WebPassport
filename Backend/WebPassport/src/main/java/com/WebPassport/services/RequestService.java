package com.WebPassport.services;

import com.WebPassport.entities.RequestEntity;
import com.WebPassport.queries.RequestQuery;
import com.WebPassport.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RequestService implements RequestRepository {

    public JdbcTemplate jdbcTemplate;
    @Autowired
    public RequestService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RequestEntity> findAllRequest() {
        return jdbcTemplate.query(RequestQuery.FIND_ALL, this::mapRowToRequestEntity);
    }

    @Override
    public List<RequestEntity> findById(int request_id) {
        return jdbcTemplate.query(RequestQuery.FIND_BY_ID, this::mapRowToRequestEntity, request_id);
    }

    @Override
    public List<RequestEntity> findByDocument_id(int document_id) {
        return jdbcTemplate.query(RequestQuery.FIND_BY_DOCUMENT_ID, this::mapRowToRequestEntity, document_id);
    }

    @Override
    public List<RequestEntity> findByOffice_id(int office_id) {
        return jdbcTemplate.query(RequestQuery.FIND_BY_OFFICE_ID, this::mapRowToRequestEntity, office_id);
    }

    @Override
    public List<RequestEntity> findByPerson_id(int person_id) {
        return jdbcTemplate.query(RequestQuery.FIND_BY_PERSON_ID, this::mapRowToRequestEntity, person_id);
    }

    @Override
    public int save(RequestEntity requestEntity) {
        return jdbcTemplate.update(RequestQuery.SAVE, requestEntity.document_id,
                requestEntity.office_id, requestEntity.person_id, requestEntity.schedule);
    }

    @Override
    public int saveAndReturnId(RequestEntity requestEntity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(RequestQuery.SAVE, new String[]{"request_id"});
            statement.setInt(1, requestEntity.document_id);
            statement.setInt(2, requestEntity.office_id);
            statement.setInt(3,requestEntity.person_id);
            statement.setString(4, requestEntity.schedule);
            return statement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private RequestEntity mapRowToRequestEntity(ResultSet resultSet, int rowNum)
        throws SQLException{
        return new RequestEntity(resultSet.getInt("request_id"),
                resultSet.getInt("document_id"),
                resultSet.getInt("office_id"),
                resultSet.getInt("person_id"),
                resultSet.getString("schedule"),
                resultSet.getString("timestamp"));
    }
}
