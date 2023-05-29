package com.WebPassport.models;

import java.util.Date;

public class Request {
    private int request_id;
    public Documents documents;
    public Date timestamp;
    public Date schedule;

    public Request(){

    }

    public Request(int request_id, Documents documents,
                   Date timestamp, Date schedule){
        this.request_id = request_id;
        this.documents = documents;
        this.timestamp = timestamp;
        this.schedule = schedule;
    }

    public Request(Documents documents,
                   Date timestamp, Date schedule){
        this.documents = documents;
        this.timestamp = timestamp;
        this.schedule = schedule;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
