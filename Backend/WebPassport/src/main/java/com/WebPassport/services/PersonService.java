package com.WebPassport.services;

import com.WebPassport.entities.PersonEntity;
import com.WebPassport.queries.PersonQuery;
import com.WebPassport.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class PersonService implements PersonRepository {

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<PersonEntity> findAllPerson() {
        return jdbcTemplate.query(PersonQuery.FIND_ALL, this::mapRowToPersonEntity);
    }

    @Override
    public List<PersonEntity> findById(int person_id) {
        return jdbcTemplate.query(PersonQuery.FIND_BY_ID, this::mapRowToPersonEntity, person_id);
    }

    @Override
    public List<PersonEntity> findByAccount_id(int account_id) {
        return jdbcTemplate.query(PersonQuery.FIND_BY_ACCOUNT_ID, this::mapRowToPersonEntity, account_id);
    }

    @Override
    public List<PersonEntity> findByAddress_id(int address_id) {
        return jdbcTemplate.query(PersonQuery.FIND_BY_ADDRESS_ID, this::mapRowToPersonEntity, address_id);
    }

    @Override
    public int save(PersonEntity personEntity) {
        return jdbcTemplate.update(PersonQuery.SAVE, personEntity.account_id,
                personEntity.address_id, personEntity.name, personEntity.nik,
                personEntity.date_of_birth, personEntity.place_of_birth, personEntity.gender);
    }

    @Override
    public PersonEntity updateAndReturnPersonEntity(int person_id, PersonEntity personEntity) {
        return jdbcTemplate.query(PersonQuery.UPDATE_AND_RETURN_PERSON, this::mapRowToPersonEntity,
                personEntity.name, personEntity.nik, personEntity.date_of_birth,
                personEntity.place_of_birth, personEntity.gender, person_id).get(0);
    }

    @Override
    public int delete(int person_id) {
        return jdbcTemplate.update(PersonQuery.DELETE, person_id);
    }

    @Override
    public int deleteByAccount_id(int account_id) {
        return jdbcTemplate.update(PersonQuery.DELETE_BY_ACCOUNT_ID, account_id);
    }

    private PersonEntity mapRowToPersonEntity(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new PersonEntity(resultSet.getInt("person_id"),
                resultSet.getInt("account_id"),
                resultSet.getInt("address_id"),
                resultSet.getString("name"),
                resultSet.getString("nik"),
                resultSet.getString("date_of_birth"),
                resultSet.getString("place_of_birth"),
                resultSet.getString("gender"));
    }
}
