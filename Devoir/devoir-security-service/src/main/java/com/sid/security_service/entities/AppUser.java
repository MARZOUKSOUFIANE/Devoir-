package com.sid.security_service.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class AppUser {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles;

    public AppUser(){
        this.roles=new ArrayList<>();
    }

    public AppUser(String username, String password, String roles ){
        this.username = username;
        this.password = password;
        this.roles=new ArrayList<>();
    }
    
    public AppUser(String username, String password){
        this.username = username;
        this.password = password;
        this.roles=new ArrayList<>();
    }

    public Long getId() { return id; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<AppRole> getRoles() {
        return roles;
    }

    public void addRole(AppRole role){
        roles.add(role);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
