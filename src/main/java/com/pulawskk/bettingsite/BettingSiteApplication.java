package com.pulawskk.bettingsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BettingSiteApplication {

    public static void main(String[] args) {
        System.out.println("first env " + System.getenv("FIRSTENV"));
        System.out.println("second env " + System.getenv("SECONDENV"));
        SpringApplication.run(BettingSiteApplication.class, args);
    }

}
