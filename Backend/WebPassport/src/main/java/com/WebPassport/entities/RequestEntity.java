package com.WebPassport.entities;

public class RequestEntity {
    public int request_id;
    public int person_id;
    public int office_id;
    public int document_id;
    public String timestamp;
    public String schedule;

    public RequestEntity(){

    }

    public RequestEntity(int request_id, int person_id,
                   int office_id, int document_id,
                   String timestamp, String schedule){
        this.request_id = request_id;
        this.person_id = person_id;
        this.office_id = office_id;
        this.document_id = document_id;
        this.timestamp = timestamp;
        this.schedule = schedule;
    }

    public RequestEntity(int person_id,
                         int office_id, int document_id,
                         String timestamp, String schedule){
        this.person_id = person_id;
        this.office_id = office_id;
        this.document_id = document_id;
        this.timestamp = timestamp;
        this.schedule = schedule;
    }
}
