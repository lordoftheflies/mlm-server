/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.dal;

import com.dd.mlm.topn.persistence.entities.AccountEntity;
import com.dd.mlm.topn.persistence.entities.NetworkNodeEntity;
import com.dd.mlm.topn.persistence.entities.NetworkNodeType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface NetworkTreeRepository extends PagingAndSortingRepository<NetworkNodeEntity, Long> {

    List<AccountEntity> findContactsOfChildNodes(@Param("accountId") UUID accountId);
    List<AccountEntity> findContactsOfRootNodes();
    List<NetworkNodeEntity> findChildren(@Param("accountId") UUID accountId);
    List<NetworkNodeEntity> findChildrenNodes(@Param("nodeId") Long nodeId);
    NetworkNodeEntity findByAccount(@Param("accountId") UUID accountId);
    Boolean isRoot(@Param("accountId") UUID accountId);
    Boolean isRootNode(@Param("nodeId") Long nodeId);
}
