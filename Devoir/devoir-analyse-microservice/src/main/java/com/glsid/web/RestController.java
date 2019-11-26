package com.glsid.web;

import com.glsid.dao.ClientRepository;
import com.glsid.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin("*")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping(path = "/photoClient/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") String id) throws IOException {
        Client client= clientRepository.findById(id).get();
        Path path= Paths.get(System.getProperty("user.home")+"/ecom/products/"+client.getPhotoName()).normalize();
        return Files.readAllBytes(path);
    }

    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadFile(MultipartFile file, @PathVariable("id")  String id) throws IOException {
        Client client=clientRepository.findById(id).get();
        client.setPhotoName(id+".jpg");
        Files.write(Paths.get(System.getProperty("user.home")+"/ecom/products/"+client.getPhotoName()),file.getBytes());
        clientRepository.save(client);
    }

}
