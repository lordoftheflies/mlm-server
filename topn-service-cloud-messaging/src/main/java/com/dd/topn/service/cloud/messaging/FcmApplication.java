package com.dd.topn.service.cloud.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = { //    AuthConfig.class,
//    MailConfig.class,
//    CmsConfiguration.class,
//    MailingServiceConfig.class,
//    NetworkServiceConfiguration.class,
//    SwaggerConfiguration.class
})
public class FcmApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FcmApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FcmApplication.class, args);
    }
}
