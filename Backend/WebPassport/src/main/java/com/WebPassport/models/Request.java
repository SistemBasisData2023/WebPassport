package com.WebPassport.models;

import java.util.Date;

public class Request {
    public enum Status{
        ACCEPTED, DECLINED, PENDING
    }
    private int request_id;
    public Documents documents;
    public Date timestamp;
    public Date schedule;
    public Status status;

    public Request(){

    }

    public Request(int request_id, Documents documents,
                   Date timestamp, Date schedule, Status status){
        this.request_id = request_id;
        this.documents = documents;
        this.timestamp = timestamp;
        this.schedule = schedule;
        this.status = status;
    }

    public Request(Documents documents,
                   Date timestamp, Date schedule, Status status){
        this.documents = documents;
        this.timestamp = timestamp;
        this.schedule = schedule;
        this.status = status;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
