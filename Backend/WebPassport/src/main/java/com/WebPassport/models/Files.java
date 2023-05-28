package com.WebPassport.models;

public class Files {
    public String files_id;
    public String name;
    public Long size;
    public String url;
    public String contentType;

    public Files(){

    }
    public Files(String files_id, String name, Long size, String url, String contentType){
        this.files_id = files_id;
        this.name = name;
        this.size = size;
        this.url = url;
        this.contentType = contentType;
    }

    public Files(String name, Long size, String url, String contentType){
        this.name = name;
        this.size = size;
        this.url = url;
        this.contentType = contentType;
    }


}
