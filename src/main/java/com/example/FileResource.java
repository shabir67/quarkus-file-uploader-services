package com.example;

import com.example.dto.*;
import com.example.service.FileService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.*;

@Path("/files")
@ApplicationScoped
public class FileResource {

    @GET
    @Path("/say_hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "welcome to pdf file services!";
    }

    @Inject
    private FileService fileService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response handleFileUploadForm(@MultipartForm MultipartFormDataInput input) {
        try {
            List<InputPart> inputParts = input.getFormDataMap().get("files");
            List<String> uploadedFileNames = fileService.uploadFiles(inputParts);

            String uploadedFileNamesStr = String.join(", ", uploadedFileNames);
            String message = "All files " + uploadedFileNamesStr + " successfully uploaded and saved to the database.";
            FileUploadResponseDto response = new FileUploadResponseDto();
            response.setMessage(message);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error uploading files.").build();
        }
    }


    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("id") Long id) {
        try {
            FileDownloadResponse fileDownloadResponse = fileService.downloadFile(id);

            byte[] rawFile = fileDownloadResponse.getContent();

            return Response.ok(rawFile)
                    .header("Content-Disposition", "attachment; filename=\"" + fileDownloadResponse.getFilename() + "\"")
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileMetadata> listFiles(){
        List<FileMetadata> fileMetadata = fileService.listFiles();
        return fileMetadata;
    }
}
