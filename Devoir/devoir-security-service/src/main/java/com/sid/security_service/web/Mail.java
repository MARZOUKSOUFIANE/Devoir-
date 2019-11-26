package com.sid.security_service.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Mail {

    private String mailTo;
    private String subject;
    private String body;
}
