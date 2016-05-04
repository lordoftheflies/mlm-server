/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.auth;

import java.io.IOException;
import java.util.Properties;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.organization}")
    private String organization;

    @Value("${mail.application}")
    private String applicationName;

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        return simpleMailMessage;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
//        javaMailSender.setUsername("heglas11@gmail.com");
        javaMailSender.setUsername("heglas11");
        javaMailSender.setPassword("Apocalypse0");
//        javaMailSender.setPort(port);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);

        return factory.createVelocityEngine();
    }

    private Properties getMailProperties() {
        Properties props = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp");
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.debug", "false");

//        props.put("mail.smtp.user", "heglas11");
//        props.put("mail.smtp.password", "Apocalypse0");
//        props.put("mail.smtp.user", "heglas11");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallbac k", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        return props;
    }
}
