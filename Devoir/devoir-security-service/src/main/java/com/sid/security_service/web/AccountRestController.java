package com.sid.security_service.web;

import com.sid.security_service.dao.AppRoleRepository;
import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.transaction.Transactional;
import java.io.IOException;

@RestController
public class AccountRestController {

    @Autowired
    AccountService accountService;
    @Autowired
    AppRoleRepository appRoleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public AppUser addAccount(@RequestBody RegisterForm userForm){
        if(!userForm.getPassword().equals(userForm.getConfirmedPassword())){
            throw new RuntimeException("you must confirm your password");
        }
        AppUser userVerify=accountService.findUserByUsername(userForm.getUsername());
        if(userVerify!=null){
            throw new RuntimeException("this user already exist");
        }
        AppUser user=new AppUser();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        return accountService.saveUser(user.getUsername(),user.getPassword(),user.getPassword());
    }

    @Transactional
    @PostMapping("/addRole")
    public AppUser addRole(@RequestBody RoleForm roleForm){
        AppRole role=appRoleRepository.findByRoleName(roleForm.getRole());
        AppUser user=accountService.findUserByUsername(roleForm.getUsername());
        if(user.getRoles().contains(role)){
            return user;
        }
        else{
            user.addRole(role);
            return user;
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordForm passwordForm) throws IOException, MessagingException {
        if(!passwordForm.getPassword().equals(passwordForm.getConfirmedPassword())){
            throw new RuntimeException("confirmation password failed");
        }
        else{
            AppUser user=accountService.findUserByUsername(passwordForm.getUsername());
            user.setPassword(passwordEncoder.encode(passwordForm.getPassword()));
            Mail mail=new Mail();
            mail.setMailTo(passwordForm.getMail());
            mail.setSubject("changing password");
            mail.setBody("Boujour \n your are changing the password of the user("+passwordForm.getUsername()+") succeessfuly");
            accountService.sendEmail(mail);
            throw  new RuntimeException("Password changed successfuly");
        }
    }

    @PostMapping("/sendMail")
    public String sendMail(/*@RequestBody Mail mail*/) throws IOException, MessagingException {

        //accountService.sendEmail(mail);
        return "mail sent successfuly";
    }
}
