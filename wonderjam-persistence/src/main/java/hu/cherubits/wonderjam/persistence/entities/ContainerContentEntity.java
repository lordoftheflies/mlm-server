/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.persistence.entities;

import hu.cherubits.wonderjam.common.ViewConstants;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author lordoftheflies
 */
@Entity
@DiscriminatorValue(value = ContainerContentEntity.RESOURCE_TYPE)
@NamedQueries({
    @NamedQuery(name = "ContainerContentEntity.findByParent", query = "SELECT c FROM ContentEntity c WHERE c.parent.id = :parentId"),
    @NamedQuery(name = "ContainerContentEntity.findRoots", query = "SELECT c FROM ContentEntity c WHERE c.parent IS NULL")
})
public class ContainerContentEntity extends ContentEntity {

    public static final String RESOURCE_TYPE = ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER;

    @OneToMany(mappedBy = "parent")
    private List<ContentEntity> children;

    public List<ContentEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ContentEntity> children) {
        this.children = children;
    }
}
