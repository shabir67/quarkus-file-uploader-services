package com.example.dto;


import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

public class FileUploadRequestDto {
    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    private String filename;
    @FormParam("content")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream content;
    public String getFilename(){
        return filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public InputStream getContent(){
        return content;
    }
    public void setContent(InputStream content){
        this.content = content;
    }
}
