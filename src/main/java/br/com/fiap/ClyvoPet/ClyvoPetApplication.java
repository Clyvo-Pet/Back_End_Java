package br.com.fiap.ClyvoPet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ClyvoPetApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClyvoPetApplication.class, args);
    }
}
