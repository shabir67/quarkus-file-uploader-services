package com.example.helper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@ApplicationScoped
public class FileUploadHelper {
    public String getFileName(MultivaluedMap<String, String> header) {
        String contentDispositionValue = header.getFirst("Content-Disposition");
        if (contentDispositionValue != null) {
            String[] contentDisposition = contentDispositionValue.split(";");
            for (String filename : contentDisposition) {
                if (filename.trim().startsWith("filename")) {
                    String[] name = filename.split("=");
                    return name[1].trim().replaceAll("\"", "");
                }
            }
        }
        return "unknown";
    }

    public byte[] readInputStream(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }
}

