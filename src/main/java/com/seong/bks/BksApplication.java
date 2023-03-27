package com.seong.bks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BksApplication.class, args);
    }

}
