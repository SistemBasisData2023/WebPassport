package com.WebPassport.entities;

public class FileEntity {
    private String files_id;

    public String name;

    public String contentType;

    public Long size;
    public byte[] data;

    public FileEntity(){

    }

    public FileEntity(String files_id, String name, String contentType, Long size, byte[] data){
        this.files_id = files_id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.data = data;
    }

    public String getFiles_id() {
        return files_id;
    }

    public void setFiles_id(String files_id) {
        this.files_id = files_id;
    }
}
