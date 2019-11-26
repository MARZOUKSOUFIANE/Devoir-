package com.sid.security_service.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterForm {

    private String username;
    private String password;
    private String confirmedPassword;
}
