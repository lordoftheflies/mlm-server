/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.topn.service.cloud.messaging;

import hu.cherubits.wonderjam.cloud.messaging.NotificationService;
import hu.cherubits.wonderjam.cloud.messaging.FcmConfiguration;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author lordoftheflies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
    FcmConfiguration.class
})
@WebAppConfiguration
public class MessagingServiceTest {
    
    public MessagingServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of send method, of class NotificationService.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        String text = "message";
        NotificationService instance = new NotificationService();
        
        Map<String, String> props = new HashMap<>();
        props.put("article", "kajsdkasjdkjasd");
        props.put("text", "Hahah");
        props.put("sender", "me");
        
        instance.send(props, SUBSCRIPTION_ID);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    private static final String SUBSCRIPTION_ID = "fguHaWEqJRc:APA91bHE3SEOiNronjd27q8QPMyNgt557UZ1mKdH7whsCG8_V_vyt49k08l58YdEUjbkGFKtN199Vg4KntP5aqcGhq-mRDpAk4c1VaebHrVoOj_SM1hwZgrS-eN-ME-tcDYgqJPbmz68";
    
}
