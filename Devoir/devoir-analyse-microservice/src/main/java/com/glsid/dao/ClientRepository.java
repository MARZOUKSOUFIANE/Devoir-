package com.glsid.dao;

import com.glsid.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface ClientRepository extends MongoRepository<Client,String> {

     List<Client> findByNomContains(String mc);
}
