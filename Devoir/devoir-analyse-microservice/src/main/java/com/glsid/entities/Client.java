package com.glsid.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String photoName;
    @JsonIgnore
    @DBRef
    private List<Analyse> listAnalysies;

    public void addAnalyse(Analyse analyse){
        listAnalysies.add(analyse);
    }
}
