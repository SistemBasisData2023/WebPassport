package com.WebPassport.services;

import com.WebPassport.entities.OfficeEntity;
import com.WebPassport.queries.OfficeQuery;
import com.WebPassport.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OfficeService implements OfficeRepository {

    public JdbcTemplate jdbcTemplate;

    @Autowired
    public OfficeService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OfficeEntity> findAllOffice() {
        return jdbcTemplate.query(OfficeQuery.FIND_ALL,this::mapRowToOfficeEntity);
    }

    @Override
    public List<OfficeEntity> findById(int office_id) {
        return jdbcTemplate.query(OfficeQuery.FIND_BY_ID, this::mapRowToOfficeEntity, office_id);
    }

    @Override
    public List<OfficeEntity> findByAddress_id(int address_id) {
        return jdbcTemplate.query(OfficeQuery.FIND_BY_ADDRESS_ID, this::mapRowToOfficeEntity, address_id);
    }

    @Override
    public int save(OfficeEntity officeEntity) {
        return jdbcTemplate.update(OfficeQuery.SAVE, officeEntity.address_id, officeEntity.name);
    }

    private OfficeEntity mapRowToOfficeEntity(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new OfficeEntity(resultSet.getInt("office_id"),
                resultSet.getInt("address_id"),
                resultSet.getString("name"));
    }
}
