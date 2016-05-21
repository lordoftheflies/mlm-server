/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author lordoftheflies
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource", discriminatorType = DiscriminatorType.STRING, length = 50)
@DiscriminatorValue(value = "resource")
@NamedQueries({
    @NamedQuery(name = "ContentEntity.findByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId ORDER BY c.orderIndex"),
    @NamedQuery(name = "ContentEntity.findDraftByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId AND c.node.contact.id = :accountId ORDER BY c.orderIndex"),
    @NamedQuery(name = "ContentEntity.findPublicByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId AND c.draft = FALSE ORDER BY c.orderIndex"),
    @NamedQuery(name = "ContentEntity.findPublishedByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId ORDER BY c.orderIndex"),
    
    @NamedQuery(name = "ContentEntity.findRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL"),
    @NamedQuery(name = "ContentEntity.findDraftRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL AND c.node.contact.id = :accountId"),
    @NamedQuery(name = "ContentEntity.findPublicRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL AND c.draft = FALSE"),
    @NamedQuery(name = "ContentEntity.findPublishedRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL"),
    
    @NamedQuery(name = "ContentEntity.findByChild", query = "SELECT c.parent FROM ContentEntity c WHERE c.id = :childId AND c.parent IS NOT NULL")
})
public class ContentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    @Basic(optional = false)
    private Integer orderIndex;

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    @Basic
    private boolean leaf = true;

    public boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    @Basic
    private boolean hasEmbeddedFile = false;

    public boolean isHasEmbeddedFile() {
        return hasEmbeddedFile;
    }

    public void setHasEmbeddedFile(boolean hasEmbeddedFile) {
        this.hasEmbeddedFile = hasEmbeddedFile;
    }
    
    

    @Column(name = "resource", insertable = false, updatable = false)
    private String resourceType;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    @Basic
    private Boolean draft = true;

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }
    
    @Basic
    private Boolean publicIndicator = true;

    public Boolean getPublicIndicator() {
        return publicIndicator;
    }

    public void setPublicIndicator(Boolean publicIndicator) {
        this.publicIndicator = publicIndicator;
    }

    @ManyToOne
    private NetworkNodeEntity node;

    public NetworkNodeEntity getNode() {
        return node;
    }

    public void setNode(NetworkNodeEntity node) {
        this.node = node;
    }
    
    @Basic
    @Column(length = 1000)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(length = 100000)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(mappedBy = "content")
    private List<MessageEntity> recipients;

    public List<MessageEntity> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<MessageEntity> recipients) {
        this.recipients = recipients;
    }

    @ManyToOne
    private ContainerContentEntity parent;

    public ContainerContentEntity getParent() {
        return parent;
    }

    public void setParent(ContainerContentEntity parent) {
        this.parent = parent;
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
        if (!(object instanceof ContentEntity)) {
            return false;
        }
        ContentEntity other = (ContentEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.digitaldefense.christeam.entities.ContentEntity[ id=" + id + " ]";
    }

}
