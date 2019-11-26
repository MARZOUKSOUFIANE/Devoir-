package com.sid.security_service.service;

import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.web.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;


public interface AccountService {
    public AppUser saveUser(String username,String password,String confirmedPassword);
    public AppRole saveRole(AppRole role);
    public void addUserRole(String username,String rolename);
    public AppUser findUserByUsername(String username);
    public void sendEmail(Mail mail) throws AddressException, MessagingException, IOException;
}
