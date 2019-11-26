package com.glsid;

import com.glsid.dao.AnalyseRepository;
import com.glsid.dao.ClientRepository;
import com.glsid.entities.Analyse;
import com.glsid.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@Transactional
public class DevoirAnalyseMicroserviceApplication {

    @Autowired
    AnalyseRepository analyseRepository;
    @Autowired
    ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(DevoirAnalyseMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(){
        return args -> {
            analyseRepository.deleteAll();
            clientRepository.deleteAll();

            Client client1=new Client(null,"soufiane","marzouk","null.png",new ArrayList<>());
            Client client2=new Client(null,"taoufiq","bahalla","null.png",new ArrayList<>());
            Client client3=new Client(null,"ali","jalal","null.png",new ArrayList<>());
            clientRepository.save(client1);clientRepository.save(client2);clientRepository.save(client3);



            Stream.of("analyse de song","analyse diabet","analyse fois","analyse coeur","analyse enceinte","analyse xxxxx","analyse yyyyy","analyse zzzz")
                    .forEach(s->{
                        Analyse analyse=new Analyse(null,s,new Date(),Math.random()*1500,"positif",true,client1);
                        analyseRepository.save(analyse);
                        client1.addAnalyse(analyse);
                        clientRepository.save(client1);
                    });

            Stream.of("analyse AAAAA","analyse BBBBB","analyse CCCC","analyse DDDDDD","analyse FFFFF","analyse RRRRR","analyse GGGG","analyse KKKKK")
                    .forEach(s->{
                        Analyse analyse=new Analyse(null,s,new Date(),Math.random()*1500,"positif",true,client2);
                        analyseRepository.save(analyse);
                        client2.addAnalyse(analyse);
                        clientRepository.save(client2);
                    });
        };

    }


}
