/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.persistence.dal;

import hu.cherubits.wonderjam.persistence.entities.ResourceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface ResourceRepository extends CrudRepository<ResourceEntity, Long>{
    
}
