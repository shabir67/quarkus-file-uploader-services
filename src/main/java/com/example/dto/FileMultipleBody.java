package com.example.dto;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;


public class FileMultipleBody {
    @FormParam("files")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream[] files;

    public FileMultipleBody() {
    }

    public InputStream[] getFiles() {
        return files;
    }

    public void setFiles(InputStream[] files) {
        this.files = files;
    }
}
