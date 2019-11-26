package com.sid.security_service.service;

import com.sid.security_service.dao.AppRoleRepository;
import com.sid.security_service.dao.AppUserRepository;
import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.web.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AppRoleRepository appRoleRepository;
    @Value("${gmail.username}")
    String username;
    @Value("${gmail.password}")
    String password;

    @Override
    public AppUser saveUser(String username, String password,String confirmedPassword) {
        AppUser user=userRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("user already exist...");
        else if(!password.equals(confirmedPassword)) throw new RuntimeException("please confirm your password...");
        else {
            user = new AppUser();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(user);
            addUserRole(username, "USER");
            return user;
        }
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addUserRole(String username, String roleName) {
        AppRole role=appRoleRepository.findByRoleName(roleName);
        AppUser user=userRepository.findByUsername(username);
        user.addRole(role);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void sendEmail(Mail mail) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getMailTo()));
        msg.setSubject(mail.getSubject());
        msg.setContent(mail.getBody(), "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mail.getBody(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("C:\\Users\\soufi\\OneDrive\\Bureau\\images\\security.png");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
