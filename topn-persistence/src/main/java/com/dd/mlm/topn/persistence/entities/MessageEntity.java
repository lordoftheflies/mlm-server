/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author lordoftheflies
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "MessageEntity.inboxByRecipient", query ="SELECT m FROM MessageEntity m WHERE m.mailBox.owner.contact.id = :recipientId"),
    @NamedQuery(name = "MessageEntity.outboxByRecipient", query ="SELECT m FROM MessageEntity m WHERE m.sender.contact.id = :senderId")
})
public class MessageEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    private ContentEntity content;

    public ContentEntity getContent() {
        return content;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    @ManyToOne
    private MailBoxEntity mailBox;

    public MailBoxEntity getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBoxEntity mailBox) {
        this.mailBox = mailBox;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic(optional = false)
    private Boolean read;

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
    
    @Basic
    @Column(length = 10000)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @ManyToOne
    private NetworkNodeEntity sender;

    public NetworkNodeEntity getSender() {
        return sender;
    }

    public void setSender(NetworkNodeEntity sender) {
        this.sender = sender;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.digitaldefense.christeam.entities.MessageEntity[ id=" + id + " ]";
    }

}
