package com.WebPassport.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {
    public enum Gender{
        MALE, FEMALE
    }
    private int person_id;
    public Address address;
    public String name;
    public String nik;
    public Date date_of_birth;
    public String place_of_birth;
    public Gender gender;
    public List<Request> requests = new ArrayList<>();

    public Person(
            int person_id,
            Address address, String name, String nik,
            Date date_of_birth, String place_of_birth, Gender gender){
        this.person_id = person_id;
        this.address = address;
        this.name = name;
        this.nik = nik;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.gender = gender;
    }

    public Person(
            Address address, String name, String nik,
            Date date_of_birth, String place_of_birth, Gender gender){
        this.address = address;
        this.name = name;
        this.nik = nik;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.gender = gender;
    }

    public int getPerson_id() {return person_id;}
    public void setPerson_id(int person_id) {this.person_id = person_id;}
}
