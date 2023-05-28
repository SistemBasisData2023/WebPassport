package com.WebPassport.models;

public class Address {
    private int address_id;
    public String address_line;
    public String subDistrict;
    public String city;
    public String province;
    public String postCode;

    public Address(){

    }

    public Address(
            String address_line,
            String subDistrict, String city,
            String province, String postCode){
        this.address_line = address_line;
        this.subDistrict = subDistrict;
        this.city = city;
        this.province = province;
        this.postCode = postCode;
    }

    public Address(
            int address_id, String address_line,
            String subDistrict, String city,
            String province, String postCode){
        this.address_id = address_id;
        this.address_line = address_line;
        this.subDistrict = subDistrict;
        this.city = city;
        this.province = province;
        this.postCode = postCode;
    }

    public int getAddress_id() {return address_id;}
    public void setAddress_id(int address_id){this.address_id = address_id;}
}
