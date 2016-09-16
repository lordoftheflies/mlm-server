package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.cloud.messaging.FcmConfiguration;
import hu.cherubits.wonderjam.cms.CmsConfiguration;
import hu.cherubits.wonderjam.mailing.MailingServiceConfig;
import hu.cherubits.wonderjam.membership.NetworkServiceConfiguration;
import hu.cherubits.wonderjam.security.AuthConfig;
import hu.cherubits.wonderjam.security.MailConfig;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
    SwaggerConfiguration.class,
    AuthConfig.class,
    MailConfig.class,
    CmsConfiguration.class,
    FcmConfiguration.class,
    MailingServiceConfig.class,
    NetworkServiceConfiguration.class,
    SecurityConfiguration.class
})
@RestController
@RequestMapping(
        consumes = {
            MediaType.APPLICATION_JSON_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE
        })
@EnableRedisHttpSession
@EnableWebMvc
public class Application extends SpringBootServletInitializer {

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Principal user(Principal user) {
        return user;
    }

    @CrossOrigin(origins = "*", allowedHeaders = {"Access-Control-Allow-Origin"}, exposedHeaders = {"Access-Control-Allow-Origin"})
    @ResponseBody
    @RequestMapping(path = "/token", method = RequestMethod.GET)
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", (session.getId() == null) ? "" : session.getId());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
