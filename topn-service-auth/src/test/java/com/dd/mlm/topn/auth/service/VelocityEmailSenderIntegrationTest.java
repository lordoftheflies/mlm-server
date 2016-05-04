package com.dd.mlm.topn.auth.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dd.mlm.topn.auth.AuthConfig;
import com.dd.mlm.topn.auth.MailConfig;
import com.dd.mlm.topn.persistence.config.DatabaseConfiguration;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author lordoftheflies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    DatabaseConfiguration.class,
    AuthConfig.class,
    MailConfig.class
})
//@PropertySource("classpath:application.properties")
@TestPropertySource("classpath:/application.properties")
public class VelocityEmailSenderIntegrationTest {

    public VelocityEmailSenderIntegrationTest() {
    }

    private static final Logger LOG = Logger.getLogger(VelocityEmailSenderIntegrationTest.class.getName());

    @Autowired
    private final UaaMailSender sender = null;

    @Autowired
    private final SimpleMailMessage msg = null;

    @Test
    public void testSendForgottenPasswordEmail() {
//        assertNotNull("VelocityEmailSender is null.", sender);
//        assertNotNull("SimpleMailMessage is null.", msg);

        sender.sendForgottenPasswordEmail(USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, PASSWORD_RESET_LINK, "en");
        sender.sendForgottenPasswordEmail(USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, PASSWORD_RESET_LINK, "hu");
        sender.sendForgottenPasswordEmail(USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, PASSWORD_RESET_LINK, "po");
    }
    public static final String PASSWORD_RESET_LINK = "http://185.51.67.30/resetpassword?=c0a1f132-1220-11e6-a148-3e1d05defe78";

    @Test
    public void testSendRegistrationActivationEmail() {
//        assertNotNull("VelocityEmailSender is null.", sender);
//        assertNotNull("SimpleMailMessage is null.", msg);

//        Map<String, Object> props = new HashMap<String, Object>();
//        props.put("firstName", "Joe");
//        props.put("lastName", "Smith");

        sender.sendRegistrationActivationEmail(INVITER_NAME, USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, REGISTRATION_LINK, ANDROID_INSTALLER_LINK, "en");
        sender.sendRegistrationActivationEmail(INVITER_NAME, USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, REGISTRATION_LINK, ANDROID_INSTALLER_LINK, "hu");
        sender.sendRegistrationActivationEmail(INVITER_NAME, USER_EMAIL, USER_NAME, APPLICATION_NAME, ORGANOIZATION_NAME, REGISTRATION_LINK, ANDROID_INSTALLER_LINK, "po");
    }
    public static final String REGISTRATION_LINK = "http://185.51.67.30/registrate?=c0b1f132-1220-11e6-a148-3e1d05defe78";
    public static final String ANDROID_INSTALLER_LINK = "http://play.google.com/topflavon";
    public static final String ORGANOIZATION_NAME = "TopFlavon Team";
    public static final String APPLICATION_NAME = "TopFlavon";
    public static final String USER_NAME = "Péczely Balázs";
    public static final String INVITER_NAME = "Hegedűs László";
    public static final String USER_EMAIL = "peczely.balazs@digitaldefense.hu";
//    public static final String USER_EMAIL = "heglas11@gmail.com";
}
