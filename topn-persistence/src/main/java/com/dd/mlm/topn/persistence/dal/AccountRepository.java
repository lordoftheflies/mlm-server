/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.dal;

import com.dd.mlm.topn.persistence.entities.AccountEntity;
import com.dd.mlm.topn.persistence.entities.NetworkNodeType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

    AccountEntity getParent(@Param("childId") UUID childId);
    
    @Override
    List<AccountEntity> findAll();

    List<AccountEntity> findRootAccounts();
    
    AccountEntity findByNetwork(@Param("nodeId") Long nodeId);
    
    
    AccountEntity findByEmail(@Param("email") String email);
    AccountEntity findByCredentials(@Param("email") String email, @Param("password") String password);
    NetworkNodeType findRoleById(@Param("id") UUID id);
}	
