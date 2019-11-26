package com.glsid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Analyse {

    @Id
    private String id;
    private String name;
    private Date date;
    private double prix;
    private String Resultat;
    private boolean resultConnu;
    @DBRef
    private Client client;
}
