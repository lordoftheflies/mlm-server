/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.service.JpaUserDetailsService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@ComponentScan(basePackageClasses = DatabaseConfiguration.class)
@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(SecurityConfiguration.class.getName());

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                    String requestedBy = request.getHeader(X_REQUESTED_BY_HEADER);
                    LOG.log(Level.INFO, "X-Requested-By: {0}", requestedBy);
                    if (requestedBy == null || requestedBy.isEmpty()) {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Basic realm");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    } else {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Application driven");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    }
                }).and()
                .authorizeRequests().antMatchers(
                        INDEX_FILE,
                        BOWER_COMPONENTS_DIRECTORY,
                        POLYMER_WIDGET_DIRECTORY,
                        IMAGES_DIRECTORY,
                        MANIFEST_FILE,
                        LOGIN_URL,
                        TOKEN_URL,
                        BASE_URL,
                        SERVICE_WORKER,
                        WEBJARS,
                        SWAGGER_CONFIGURATION,
                        SWAGGER_RESOURCES,
                        SWAGGER_UI,
                        SWAGGER_API).permitAll().anyRequest().authenticated().and()
                .logout().permitAll().deleteCookies(REMEMBER_ME_TOKEN, XXSRFTOKEN2).and()
                //                .and().formLogin().loginPage("/login-view").loginProcessingUrl(LOGIN_URL).usernameParameter("userName").passwordParameter("password").defaultSuccessUrl(BASE_URL)
                .rememberMe().rememberMeServices(rememberMeServices).and()
                //                .rememberMeParameter(REMEMBER_ME_TOKEN).rememberMeServices(rememberMeServices).tokenValiditySeconds(3600)
                .csrf().csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }
    private static final String REMEMBER_ME_TOKEN = "remember-me";

    CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(XXSRFTOKEN);
        return repository;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/backend")
                        .allowedOrigins("*")
                        .allowedMethods("POST, GET, OPTIONS, DELETE ,PUT")
                        .allowedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .exposedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

    @Autowired
    @Bean
    TokenBasedRememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        return new TokenBasedRememberMeServices(REMEMBER_ME_TOKEN, userDetailsService);
    }

    private static final String WEBJARS = "/webjars/**";
    private static final String SWAGGER_UI = "/swagger-ui.html";
    private static final String SWAGGER_RESOURCES = "/swagger-resources";
    private static final String SWAGGER_CONFIGURATION = "/configuration/**";
    private static final String SWAGGER_API = "/v2/**";
    private static final String BASE_URL = "/";
    private static final String BACKEND_URL = "/backend/**";
    private static final String LOGIN_URL = "/login";
    private static final String TOKEN_URL = "/token";
    private static final String MANIFEST_FILE = "/manifest.json";
    private static final String INDEX_FILE = "/index.html";
    private static final String IMAGES_DIRECTORY = "/images/**";
    private static final String POLYMER_WIDGET_DIRECTORY = "/src/**";
    private static final String BOWER_COMPONENTS_DIRECTORY = "/bower_components/**";

    private static final String SERVICE_WORKER = "/service-worker.js";

    private static final String AC_REQUEST_HEADERS_HEADER = "Access-Control-Request-Headers";
    private static final String AC_REQUEST_METHOD_HEADER = "Access-Control-Request-Method";
    private static final String AC_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String AC_ALLOW_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";
    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";
    private static final String X_REQUESTED_BY_HEADER = "X-Requested-By";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String ACCEPT_HEADER = "accept";
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String AUTHENTICATION_HEADER = "WWW-Authenticate";
    private static final String XXSRFTOKEN = "X-XSRF-TOKEN";
    private static final String XXSRFTOKEN2 = "XSRF-TOKEN";

}
