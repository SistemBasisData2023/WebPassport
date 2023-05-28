package com.WebPassport.entities;


public class DocumentsEntity {
    public int document_id;
    public String ktp_files_id;
    public String kk_files_id;

    public DocumentsEntity(){

    }
    public DocumentsEntity(int document_id, String ktp_files_id, String kk_files_id){
        this.document_id = document_id;
        this.ktp_files_id = ktp_files_id;
        this.kk_files_id = kk_files_id;
    }
    public DocumentsEntity(String ktp_files_id, String kk_files_id){
        this.ktp_files_id = ktp_files_id;
        this.kk_files_id = kk_files_id;
    }
}
