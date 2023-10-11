package com.example;

import com.example.helper.FileUploadHelper;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileUploadHelperTest {

    private FileUploadHelper fileUploadHelper;

    @BeforeEach
    void setup() {
        fileUploadHelper = new FileUploadHelper();
    }

    @Test
    void testGetFileNameHappyPath() {
        MultivaluedMap<String, String> header = new MultivaluedHashMap<>();
        header.add("Content-Disposition", "form-data; name=\"file\"; filename=\"supersemar.txt\"");
        String fileName = fileUploadHelper.getFileName(header);
        assertEquals("supersemar.txt", fileName);
    }

    @Test
    void testGetFileNameNoContentDispositionHeader() {
        MultivaluedMap<String, String> header = new MultivaluedHashMap<>();
        String fileName = fileUploadHelper.getFileName(header);
        assertEquals("unknown", fileName);
    }

    @Test
    void testGetFileNameNoFilenameInContentDisposition() {
        MultivaluedMap<String, String> header = new MultivaluedHashMap<>();
        header.add("Content-Disposition", "form-data; name=\"file\"");
        String fileName = fileUploadHelper.getFileName(header);
        assertEquals("unknown", fileName);
    }

    @Test
    void testReadInputStreamHappyPath() throws IOException {
        byte[] expectedData = "Salamualaikum".getBytes();
        InputStream inputStream = new ByteArrayInputStream(expectedData);
        byte[] result = fileUploadHelper.readInputStream(inputStream);
        assertArrayEquals(expectedData, result);
    }

    @Test
    void testReadInputStreamIOException() {
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated IOException");
            }
        };
        assertThrows(IOException.class, () -> fileUploadHelper.readInputStream(inputStream));
    }
}

