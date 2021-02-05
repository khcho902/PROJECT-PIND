package io.spring.pind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PindApplication {

    public static void main(String[] args) {
        SpringApplication.run(PindApplication.class, args);
    }

}
