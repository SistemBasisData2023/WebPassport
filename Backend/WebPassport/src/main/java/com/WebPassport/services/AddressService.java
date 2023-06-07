package com.WebPassport.services;

import com.WebPassport.models.Address;
import com.WebPassport.queries.AddressQuery;
import com.WebPassport.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AddressService implements AddressRepository {

    public JdbcTemplate jdbcTemplate;
    @Autowired
    public AddressService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Address> findAllAddress() {
        return jdbcTemplate.query(AddressQuery.FIND_ALL, this::mapRowToAddress);
    }

    @Override
    public List<Address> findByAddress_line(String address_line) {
        return jdbcTemplate.query(AddressQuery.FIND_BY_ADDRESS_LINE, this::mapRowToAddress, address_line);
    }

    @Override
    public List<Address> findBySubDistrict(String subDistrict) {
        return jdbcTemplate.query(AddressQuery.FIND_BY_SUBDISTRICT, this::mapRowToAddress, subDistrict);
    }

    @Override
    public List<Address> findByCity(String city) {
        return jdbcTemplate.query(AddressQuery.FIND_BY_CITY, this::mapRowToAddress, new String[]{"%"+city+"%"});
    }

    @Override
    public List<Address> findByProvince(String province) {
        return jdbcTemplate.query(AddressQuery.FIND_BY_PROVINCE, this::mapRowToAddress, province);
    }

    @Override
    public List<Address> findById(int address_id) {
        return jdbcTemplate.query(AddressQuery.FIND_BY_ID, this::mapRowToAddress, address_id);
    }

    @Override
    public int save(Address address) {
        return jdbcTemplate.update(AddressQuery.SAVE,
                address.address_line, address.subDistrict,
                address.city, address.province, address.postCode);
    }

    @Override
    public int saveAndReturnId(Address address) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(AddressQuery.SAVE, new String[]{"address_id"});
            statement.setString(1, address.address_line);
            statement.setString(2, address.subDistrict);
            statement.setString(3, address.city);
            statement.setString(4, address.province);
            statement.setString(5, address.postCode);
            return statement;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Address saveAndReturnAddress(Address address) {
        return jdbcTemplate.query(AddressQuery.SAVE_AND_RETURN_ADDRESS, this::mapRowToAddress,
                address.address_line, address.subDistrict, address.city,
                address.province, address.postCode).get(0);
    }

    @Override
    public Address updateAndReturnAddress(int address_id, Address address) {
        return jdbcTemplate.query(AddressQuery.UPDATE_AND_RETURN_ADDRESS, this::mapRowToAddress,
                address.address_line, address.subDistrict, address.city,
                address.province, address.postCode, address_id).get(0);
    }

    @Override
    public int delete(int address_id) {
        return jdbcTemplate.update(AddressQuery.DELETE, address_id);
    }

    private Address mapRowToAddress(ResultSet resultSet, int rowNum)
            throws SQLException {

        return new Address(resultSet.getInt("address_id"),
                resultSet.getString("address_line"),
                resultSet.getString("sub_district"),
                resultSet.getString("city"),
                resultSet.getString("province"),
                resultSet.getString("postcode"));
    }
}
