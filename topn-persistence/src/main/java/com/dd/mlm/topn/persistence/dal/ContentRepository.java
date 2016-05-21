/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.dal;

import com.dd.mlm.topn.persistence.entities.ContentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lordoftheflies
 */
public interface ContentRepository extends PagingAndSortingRepository<ContentEntity, UUID> {

    @Override
    public List<ContentEntity> findAll(Sort sort);

    @Override
    public List<ContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<ContentEntity> findAll();

    public List<ContentEntity> findByParent(@Param("parentId") UUID parentId);
    public List<ContentEntity> findPublicByParent(@Param("parentId") UUID parentId);

    public List<ContentEntity> findPublishedByParent(@Param("parentId") UUID parentId);

    public List<ContentEntity> findDraftByParent(@Param("parentId") UUID parentId, @Param("accountId") UUID accountId);
//    

    public ContentEntity findByChild(@Param("childId") UUID childId);

    public List<ContentEntity> findRoots();
    
    public List<ContentEntity> findPublicRoots();

    public List<ContentEntity> findPublishedRoots();

    public List<ContentEntity> findDraftRoots(@Param("accountId") UUID accountId);
}
