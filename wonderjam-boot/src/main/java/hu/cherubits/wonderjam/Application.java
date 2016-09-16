package hu.cherubits.wonderjam;

import com.dd.mlm.topn.auth.AuthConfig;
import com.dd.mlm.topn.auth.MailConfig;
import com.dd.mlm.topn.cms.CmsConfiguration;
import com.dd.mlm.topn.mailing.MailingServiceConfig;
import com.dd.mlm.topn.network.NetworkServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
    AuthConfig.class,
    MailConfig.class,
    CmsConfiguration.class,
    MailingServiceConfig.class,
    NetworkServiceConfiguration.class,
    SwaggerConfiguration.class
})
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
