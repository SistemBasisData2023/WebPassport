package com.WebPassport.entities;

import com.WebPassport.models.Person;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonEntity {
    public int person_id;
    public int account_id;
    public int address_id;
    public String name;
    public String nik;
    public String date_of_birth;
    public String place_of_birth;
    public String gender;

    public PersonEntity(){

    }

    public PersonEntity(
            int person_id, int account_id,
            int address_id, String name, String nik,
            String date_of_birth, String place_of_birth, String gender){
        this.person_id = person_id;
        this.account_id = account_id;
        this.address_id = address_id;
        this.name = name;
        this.nik = nik;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.gender = gender;
    }

    public PersonEntity(
            int account_id,
            int address_id, String name, String nik,
            String date_of_birth, String place_of_birth, String gender){
        this.account_id = account_id;
        this.address_id = address_id;
        this.name = name;
        this.nik = nik;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.gender = gender;
    }

}
