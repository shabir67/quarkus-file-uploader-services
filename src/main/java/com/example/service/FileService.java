package com.example.service;

import com.example.dto.FileDownloadResponse;
import com.example.dto.FileMetadata;
import com.example.entity.FileEntity;
import com.example.helper.FileUploadHelper;
import com.example.repository.FileRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MultivaluedMap;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileService {
    @Inject
    public
    FileRepository fileRepository;

    @Inject
    public
    FileUploadHelper fileUploadHelper;

    @Transactional
    public List<String> uploadFiles(List<InputPart> inputParts) throws IOException {
        List<String> uploadedFileNames = new ArrayList<>();

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            String fileName = fileUploadHelper.getFileName(header);
            uploadedFileNames.add(fileName);

            InputStream inputStream = inputPart.getBody(InputStream.class, null);
            byte[] fileContent = fileUploadHelper.readInputStream(inputStream);

            // Save file information to the database
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(fileName);
            fileEntity.setContent(fileContent);
            fileRepository.persist(fileEntity);
        }

        return uploadedFileNames;
    }

    @Transactional
    public FileDownloadResponse downloadFile(Long id) {
        try {
            FileEntity fileEntity = fileRepository.findById(id);

            if (fileEntity == null) {
                throw new NotFoundException("File not found!");
            }

            FileDownloadResponse response = new FileDownloadResponse();
            response.setFilename(fileEntity.getFilename());
            response.setContent(fileEntity.getContent());

            return response;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error downloading file.");
        }
    }

    @Transactional
    public List<FileMetadata> listFiles(){
        try {
            List<FileEntity> fileEntities = fileRepository.listAll();

            return fileEntities.stream()
                    .map(fileEntity -> {
                        FileMetadata fileMetadata = new FileMetadata();
                        fileMetadata.setId(fileEntity.getId());
                        fileMetadata.setFilename(fileEntity.getFilename());
                        return fileMetadata;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error listing files.");
        }
    }

}
