package com.example.liquibasedemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class LiquibaseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiquibaseDemoApplication.class, args);
    }

//   @Bean
//   CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
//		return args->{
//            for(int i =1;i<10;i++){
//                kafkaTemplate.send("test1","Hello Kafka "+i);
//            }
//
//		};
//    }
}
