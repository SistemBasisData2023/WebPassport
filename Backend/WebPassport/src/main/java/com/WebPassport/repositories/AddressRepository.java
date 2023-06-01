package com.WebPassport.repositories;

import com.WebPassport.models.Address;

import java.util.List;

public interface AddressRepository {
    List<Address> findAllAddress();
    List<Address> findByAddress_line(String address_line);
    List<Address> findBySubDistrict(String subDistrict);
    List<Address> findByCity(String City);
    List<Address> findByProvince(String Province);
    List<Address> findById(int address_id);
    int save(Address address);
    int saveAndReturnId(Address address);
    Address saveAndReturnAddress(Address address);
    Address updateAndReturnAddress(int address_id, Address address);
    int delete(int address_id);
}
