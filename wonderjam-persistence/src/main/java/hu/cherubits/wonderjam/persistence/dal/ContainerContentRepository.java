/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.persistence.dal;

import hu.cherubits.wonderjam.persistence.entities.ContainerContentEntity;
import hu.cherubits.wonderjam.persistence.entities.ContentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface ContainerContentRepository extends PagingAndSortingRepository<ContainerContentEntity, UUID> {

    @Override
    public List<ContainerContentEntity> findAll(Sort sort);

    @Override
    public List<ContainerContentEntity> findAll(Iterable<UUID> itrbl);

    @Override
    public List<ContainerContentEntity> findAll();

    public List<ContainerContentEntity> findByParent(@Param("parentId") UUID parentId);
//    
//    public List<ContentEntity> findByChildren(@Param("childId") UUID childId);
    
    public List<ContentEntity> findRoots();
}
