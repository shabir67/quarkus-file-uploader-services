package com.example.entity;


import jakarta.persistence.*;

@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    @Lob
    @Column(name = "content", columnDefinition = "LONGBLOB")
    private byte [] content;
    public Long getId(){
        return  id;
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
    public byte[] getContent(){
        return content;
    }
    public void setContent(byte[] content){
        this.content = content;
    }
}
