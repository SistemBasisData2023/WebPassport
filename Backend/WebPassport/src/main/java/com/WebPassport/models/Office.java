package com.WebPassport.models;

public class Office {
    private int office_id;
    public String name;
    public Address address;

    public Office(int office_id, Address address, String name){
        this.office_id = office_id;
        this.name = name;
        this.address = address;
    }
    public Office(Address address, String name){
        this.name = name;
        this.address = address;
    }

    public int getOffice_id() {return office_id;}
    public void setOffice_id(int office_id) {this.office_id = office_id;}
}
