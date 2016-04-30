/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.mailing.service;

import com.dd.mlm.topn.mailing.model.NotificationDto;
import com.dd.mlm.topn.persistence.dal.AccountRepository;
import com.dd.mlm.topn.persistence.dal.ContentRepository;
import com.dd.mlm.topn.persistence.dal.MessageRepository;
import com.dd.mlm.topn.persistence.entities.MessageEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/mailbox")
public class MailBoxService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ContentRepository contentRepository;

    @RequestMapping(path = "/{accountId}/inbox", method = RequestMethod.GET)
    public List<NotificationDto> inbox(@PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        return messageRepository.inboxByRecipient(id).stream()
                .map((MessageEntity e) -> new NotificationDto(
                        e.getId().toString(),
                        accountId,
                        accountRepository.findOne(id).getName(),
                        e.getText(),
                        e.getContent().getTitle(),
                        e.getContent().getId().toString()))
                .collect(Collectors.toList());
    }
    
    @RequestMapping(path = "/{accountId}/outbox", method = RequestMethod.GET)
    public List<NotificationDto> outbox(@PathVariable("accountId") String accountId) {
        UUID id = UUID.fromString(accountId);
        return messageRepository.inboxByRecipient(id).stream()
                .map((MessageEntity e) -> new NotificationDto(
                        e.getId().toString(),
                        accountId,
                        accountRepository.findOne(id).getName(),
                        e.getText(),
                        e.getContent().getTitle(),
                        e.getContent().getId().toString()))
                .collect(Collectors.toList());
    }
}
