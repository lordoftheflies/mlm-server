package com.dd.topn.service.cloud.messaging;

import org.springframework.context.annotation.Bean;
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
//public class FcmConfiguration extends SpringBootServletInitializer {
public class FcmConfiguration {

    @Bean
    public NotificationService notificationService() {
        return new NotificationService();
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(FcmConfiguration.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(FcmConfiguration.class, args);
//    }
}
