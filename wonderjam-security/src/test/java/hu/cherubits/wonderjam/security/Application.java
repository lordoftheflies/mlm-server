package hu.cherubits.wonderjam.security;

import hu.cherubits.wonderjam.persistence.config.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackageClasses = {
            DatabaseConfiguration.class,
            AuthConfig.class,
            MailConfig.class
        },
        basePackages = {
            "hu.cherubits.wonderjam.persistence.entities",
            "hu.cherubits.wonderjam.persistence.dal"
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
