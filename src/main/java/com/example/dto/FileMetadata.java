package com.example.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class FileMetadata {
    private Long id;
    private String filename;
    @JsonbCreator
    public FileMetadata(@JsonbProperty("id") Long id, @JsonbProperty("filename") String filename) {
        this.id = id;
        this.filename = filename;
    }

    public FileMetadata() {

    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
}
