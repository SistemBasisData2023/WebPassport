package com.WebPassport.services;

import com.WebPassport.entities.OfficeEntity;
import com.WebPassport.queries.OfficeQuery;
import com.WebPassport.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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

    @Override
    public int saveAndReturnId(OfficeEntity officeEntity){
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(OfficeQuery.SAVE, new String[]{"office_id"});
            statement.setInt(1, officeEntity.address_id);
            statement.setString(2, officeEntity.name);
            return statement;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public OfficeEntity saveAndReturnOffice(OfficeEntity officeEntity) {
        return jdbcTemplate.query(OfficeQuery.SAVE_AND_RETURN_OFFICE, this::mapRowToOfficeEntity,
                officeEntity.address_id, officeEntity.name).get(0);
    }

    @Override
    public OfficeEntity updateAndReturnOffice(int office_id, String name) {
        return jdbcTemplate.query(OfficeQuery.UPDATE_AND_RETURN_OFFICE, this::mapRowToOfficeEntity,
                name, office_id).get(0);
    }

    @Override
    public int delete(int office_id) {
        return jdbcTemplate.update(OfficeQuery.DELETE, office_id);
    }

    private OfficeEntity mapRowToOfficeEntity(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new OfficeEntity(resultSet.getInt("office_id"),
                resultSet.getInt("address_id"),
                resultSet.getString("name"));
    }
}
