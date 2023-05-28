package com.WebPassport.services;

import com.WebPassport.models.Address;
import com.WebPassport.queries.AddressQuery;
import com.WebPassport.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
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
        return jdbcTemplate.query(AddressQuery.FIND_BY_CITY, this::mapRowToAddress, city);
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
