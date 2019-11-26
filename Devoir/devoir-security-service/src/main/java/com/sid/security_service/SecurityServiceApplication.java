package com.sid.security_service;

import com.sid.security_service.dao.AppRoleRepository;
import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.service.AccountService;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
//@EnableSwagger2
public class SecurityServiceApplication {

    @Autowired
    AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(){
        return  args -> {
            accountService.saveRole(new AppRole(null,"ANALYSE_MANAGER"));
            accountService.saveRole(new AppRole(null,"USER"));
            accountService.saveRole(new AppRole(null,"ADMIN"));

            Stream.of("user1","user2","user3","user4","admin").forEach(u->{
                accountService.saveUser(u,"1234","1234");
            });
            accountService.addUserRole("admin","ANALYSE_MANAGER");
            accountService.addUserRole("user1","ANALYSE_MANAGER");
            accountService.addUserRole("admin","ADMIN");

        };
    }

    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    ServletWebServerFactory servletWebContainer(){
        //Enable SSL Trafic
         TomcatServletWebServerFactory tomcat=  new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint=new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection securityCollection=new SecurityCollection();
                securityCollection.addPattern("/*");
                securityConstraint.addCollection(securityCollection);
                context.addConstraint(securityConstraint);
            }
        };
        
        //Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
        return tomcat;
    }

    //redirect frop http to https... any request for 8080 to be redirect to HTTPS on 443
    private Connector httpToHttpsRedirectConnector() {
        Connector connector=new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }*/

}
