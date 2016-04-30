package com.dd.topn;

import com.dd.mlm.topn.auth.AuthConfig;
import com.dd.mlm.topn.cms.CmsConfiguration;
import com.dd.mlm.topn.mailing.MailingServiceConfig;
import com.dd.mlm.topn.network.NetworkServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
    AuthConfig.class,
    CmsConfiguration.class,
    MailingServiceConfig.class,
    NetworkServiceConfiguration.class,
    SwaggerConfiguration.class
})
public class ChristeamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChristeamServerApplication.class, args);
    }
}
