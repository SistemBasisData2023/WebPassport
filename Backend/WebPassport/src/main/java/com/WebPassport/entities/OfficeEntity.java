package com.WebPassport.entities;

public class OfficeEntity {
    public int office_id;
    public int address_id;
    public String name;

    public OfficeEntity(){

    }
    public OfficeEntity(int office_id, int address_id, String name){
        this.office_id = office_id;
        this.address_id = address_id;
        this.name = name;
    }
    public OfficeEntity(int address_id, String name){
        this.address_id = address_id;
        this.name = name;
    }
}
