package com.WebPassport.controllers;

import com.WebPassport.entities.OfficeEntity;
import com.WebPassport.mapper.ObjectMapper;
import com.WebPassport.models.Address;
import com.WebPassport.models.Office;
import com.WebPassport.repositories.AddressRepository;
import com.WebPassport.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/office")
public class OfficeController {
    public OfficeRepository officeRepository;
    public AddressRepository addressRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public OfficeController(OfficeRepository officeRepository, AddressRepository addressRepository, ObjectMapper objectMapper){
        this.officeRepository = officeRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getOffice(
            @RequestParam(required = false) Integer office_id,
            @RequestParam(required = false) Integer address_id
    ){
        try {
            List<OfficeEntity> officeEntityList = new ArrayList<>();
            if(office_id != null)
                officeEntityList.addAll(officeRepository.findById(office_id));
            else if(address_id != null)
                officeEntityList.addAll(officeRepository.findByAddress_id(address_id));
            else
                officeEntityList.addAll(officeRepository.findAllOffice());

            if(officeEntityList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Office> officeList = new ArrayList<>();
            for(OfficeEntity officeEntity: officeEntityList){
                officeList.add(objectMapper.mapToOffice(officeEntity));
            }

            return new ResponseEntity<>(officeList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOffice(
            @RequestParam String name,
            @RequestParam String address_line,
            @RequestParam String subDistrict,
            @RequestParam String city,
            @RequestParam String province,
            @RequestParam String postCode
    ){
        try {
            Address _address = new Address(address_line, subDistrict, city, province, postCode);
            addressRepository.save(_address);

            OfficeEntity officeEntity = officeRepository.saveAndReturnOffice(
                    new OfficeEntity(
                            addressRepository.findByAddress_line(_address.address_line).get(0).getAddress_id(),
                            name));
            Office office = objectMapper.mapToOffice(officeEntity);
            return new ResponseEntity<>(office, HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{office_id}/update")
    public ResponseEntity<?> updateOffice(@PathVariable int office_id, String name){
        try {
            OfficeEntity officeEntity = officeRepository.updateAndReturnOffice(office_id, name);
            return new ResponseEntity<>(officeEntity, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{office_id}/delete")
    public ResponseEntity<?> deleteOffice(@PathVariable int office_id){
        try {
            List<OfficeEntity> officeEntityList = new ArrayList<>(officeRepository.findById(office_id));
            if(officeEntityList.isEmpty()){
                return new ResponseEntity<>("Office Not Found", HttpStatus.NO_CONTENT);
            }
            OfficeEntity officeEntity = officeEntityList.get(0);
            int addressRows = addressRepository.delete(officeEntity.address_id);
            int rows = officeRepository.delete(office_id);
            return new ResponseEntity<>("Delete "+rows+" Office and "+addressRows+" Address", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
