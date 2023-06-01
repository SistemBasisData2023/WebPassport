package com.WebPassport.entities;

public class RequestEntity {
    public int request_id;
    public int document_id;
    public int office_id;
    public int person_id;
    public String schedule;
    public String timestamp;
    public String status;


    public RequestEntity(){

    }

    public RequestEntity(int request_id, int document_id, int office_id,
                          int person_id, String schedule, String timestamp, String status){
        this.request_id = request_id;
        this.person_id = person_id;
        this.office_id = office_id;
        this.document_id = document_id;
        this.timestamp = timestamp;
        this.schedule = schedule;
        this.status = status;
    }

    public RequestEntity(int document_id, int office_id, int person_id,
                          String schedule, String timestamp, String status){
        this.person_id = person_id;
        this.office_id = office_id;
        this.document_id = document_id;
        this.timestamp = timestamp;
        this.schedule = schedule;
        this.status = status;
    }

    public RequestEntity(int document_id, int office_id, int person_id,
                         String schedule, String status){
        this.person_id = person_id;
        this.office_id = office_id;
        this.document_id = document_id;
        this.schedule = schedule;
        this.status = status;
    }
}
