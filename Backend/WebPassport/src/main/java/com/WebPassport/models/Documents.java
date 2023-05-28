package com.WebPassport.models;


public class Documents {
    private int document_id;
    public Files ktp_files;
    public Files kk_files;

    public Documents(){

    }
    public Documents(int document_id, Files ktp_files, Files kk_files){
        this.document_id = document_id;
        this.ktp_files = ktp_files;
        this.kk_files = kk_files;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }
}
