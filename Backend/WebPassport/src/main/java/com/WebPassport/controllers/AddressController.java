package com.WebPassport.controllers;


import com.WebPassport.models.Address;
import com.WebPassport.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    public AddressRepository addressRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAddress(
            @RequestParam(required = false) Integer address_id,
            @RequestParam(required = false) String city
    ){
        try {
            List<Address> addressList = new ArrayList<>();
            if(address_id != null)
                addressList.addAll(addressRepository.findById(address_id));
            else if (city != null)
                addressList.addAll(addressRepository.findByCity(city));
            else
                addressList.addAll(addressRepository.findAllAddress());

            if(addressList.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addAddress(
            @RequestParam String address_line,
            @RequestParam String subDistrict,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String postNumber){
        try {
            int rows = addressRepository.save(new Address(
                    address_line, subDistrict,
                    city, province, postNumber)
            );
            return new ResponseEntity<>("Create" + rows + "Address" , HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
