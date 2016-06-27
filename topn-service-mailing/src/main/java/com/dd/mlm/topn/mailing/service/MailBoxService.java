/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.mailing.service;

import com.dd.mlm.topn.mailing.model.SharingDto;
import com.dd.mlm.topn.mailing.model.NotificationDto;
import com.dd.mlm.topn.persistence.dal.AccountRepository;
import com.dd.mlm.topn.persistence.dal.ContentRepository;
import com.dd.mlm.topn.persistence.dal.MailBoxRepository;
import com.dd.mlm.topn.persistence.dal.MessageRepository;
import com.dd.mlm.topn.persistence.dal.NetworkTreeRepository;
import com.dd.mlm.topn.persistence.entities.MailBoxEntity;
import com.dd.mlm.topn.persistence.entities.MessageEntity;
import com.dd.mlm.topn.persistence.entities.NetworkNodeEntity;
import com.dd.topn.service.cloud.messaging.NotificationService;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/mailbox")
public class MailBoxService {

    private static final Logger LOG = Logger.getLogger(MailBoxService.class.getName());

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NetworkTreeRepository networkTreeRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MailBoxRepository mailBoxRepository;
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(path = "/{accountId}/inbox", method = RequestMethod.GET)
    public List<NotificationDto> inbox(@PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        LOG.log(Level.INFO, "Read inbox of recipient[{0}] ...", id);
        return messageRepository.inboxByRecipient(id).stream()
                .map((MessageEntity e) -> new NotificationDto(
                        e.getId().toString(),
                        accountId,
                        accountRepository.findOne(id).getName(),
                        e.getText(),
                        (e.getContent() != null) ? e.getContent().getTitle() : null,
                        (e.getContent() != null) ? e.getContent().getId().toString() : null,
                        e.getText()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{accountId}/outbox", method = RequestMethod.GET)
    public List<NotificationDto> outbox(@PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        LOG.log(Level.INFO, "Read outbox of recipient[{0}] ...", id);
        return messageRepository.outboxByRecipient(id).stream()
                .map((MessageEntity e) -> new NotificationDto(
                        e.getId().toString(),
                        accountId,
                        accountRepository.findOne(id).getName(),
                        e.getText(),
                        (e.getContent() != null) ? e.getContent().getTitle() : null,
                        (e.getContent() != null) ? e.getContent().getId().toString() : null,
                        e.getText()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{accountId}/send", method = RequestMethod.POST)
    public ResponseEntity sendMessage(@PathVariable("accountId") String accountId, @RequestBody SharingDto model) {
        try {
            UUID id = UUID.fromString(accountId);
            LOG.log(Level.INFO, "Recipient[{0}] send a message: {1}", new Object[]{
                id,
                model.getMessage()
            });
            MessageEntity message = new MessageEntity();
            message.setRead(false);
            message.setText(model.getMessage());

            try {

                message.setContent(contentRepository.findOne(model.getContentId()));
            } catch (Exception ex) {
                LOG.log(Level.WARNING, "Content not added.", ex);
            }

            final NetworkNodeEntity sender = networkTreeRepository.findByAccount(id);
            message.setSender(sender);

            // Save a message to all inbox.
            model.getRecipients().forEach(a -> {
                try {
                    final MailBoxEntity recipientMalboxEntity = mailBoxRepository.findByRecipient(a);
                    message.setMailBox(recipientMalboxEntity);
                    MessageEntity newMessage = messageRepository.save(message);
                    LOG.log(Level.INFO, "Add message[{0}] to inbox of recipient[{1}]", new Object[]{
                        a,
                        newMessage.getId()
                    });
                    // TODO: push the notification.
//                    restTemplate.postForObject(new URI(accountId), , responseType)

                    // Try notify users.
                    try {
                        notificationService.send(new HashMap<>(), sender.getId().toString());
                    } catch (Exception ex) {
                        LOG.log(Level.WARNING, "Cloud not notify recipient.", ex);
                    }
                } catch (Exception e) {
                    LOG.log(Level.INFO, "Message sending failed", e);
                }
            });

            // Respond to the sending.
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
        }
    }
}
