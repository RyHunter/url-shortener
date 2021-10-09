package com.shortnr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.shortnr")
@EnableJpaRepositories("com.shortnr.repository")
public class ShortnrApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShortnrApplication.class, args);
  }

}