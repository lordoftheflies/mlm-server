/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.topn.service.cloud.messaging;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import static java.awt.SystemColor.text;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author lordoftheflies
 */
@Service
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

//    private String fcmServerKey = "AIzaSyBY6sCStgF8ucCSFIYpKJiPxMJ3VMT-28o";
    @Value("${fcm.serverkey}")
    private String fcmServerKey = "AIzaSyBY6sCStgF8ucCSFIYpKJiPxMJ3VMT-28o";
//    private String fcmSenderId = "522589320362";
    @Value("${fcm.senderid}")
    private String fcmSenderId = "522589320362";
//    private String cloudMessagingServer = "https://topflavon-paas.firebaseapp.com";
    @Value("${fcm.server}")
    private String cloudMessagingServer = "https://topflavon-paas.firebaseapp.com";
//    private String fileName = "topflavon-909a071a2050.json";
    @Value("${fcm.credentials}")
    private String fileName = "topflavon-909a071a2050.json";

    private static FirebaseOptions options = null;

    public NotificationService() {

//        try {
        if (options == null) {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(getClass().getClassLoader().getResourceAsStream(fileName))
                    .setDatabaseUrl(cloudMessagingServer)
                    .build();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

            FirebaseApp.initializeApp(options);

        }
    }
    
    private void append(Message.Builder builder, String key, String value) {
        builder.collapseKey(key).addData(key, value);
    }

    public void send(Map<String, String> props, String... to) {

        try {
            LOG.log(Level.INFO, "SEND MESSAGE ...");
            LOG.log(Level.INFO, "FCM Server Key: {0}", fcmServerKey);
            LOG.log(Level.INFO, "FCM Sender ID: {0}", fcmSenderId);
            LOG.log(Level.INFO, "Properties: {0}", props.toString());

            Sender sender = new FcmSender(fcmServerKey);

            Message.Builder builder = new Message.Builder()
                    .timeToLive(3)
                    .delayWhileIdle(true);
            
            props.forEach((String key, String value) -> append(builder, key, value));
            
            Message message = builder.build();

            // Use the same token(or registration id) that was earlier
            // used to send the message to the client directly from
            // Firebase Console's Notification tab.
            Result result = sender.send(message, to[0], 1);
            LOG.log(Level.INFO, "Result: {0}", result.toString());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not send message.", ex);
        }
    }
}