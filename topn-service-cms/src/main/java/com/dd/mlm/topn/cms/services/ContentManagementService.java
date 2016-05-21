/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.cms.services;

import com.dd.mlm.topn.cms.model.MessageDto;
import com.dd.mlm.topn.cms.model.PageDto;
import com.dd.mlm.topn.cms.model.SectionDto;
import com.dd.mlm.topn.common.ViewConstants;
import com.dd.mlm.topn.exceptions.ContentNotFoundException;
import com.dd.mlm.topn.persistence.dal.AccountRepository;
import com.dd.mlm.topn.persistence.dal.ContainerContentRepository;
import com.dd.mlm.topn.persistence.dal.ContentRepository;
import com.dd.mlm.topn.persistence.dal.MailBoxRepository;
import com.dd.mlm.topn.persistence.dal.MessageRepository;
import com.dd.mlm.topn.persistence.dal.NetworkTreeRepository;
import com.dd.mlm.topn.persistence.entities.ContainerContentEntity;
import com.dd.mlm.topn.persistence.entities.ContentEntity;
import com.dd.mlm.topn.persistence.entities.ImageContentEntity;
import com.dd.mlm.topn.persistence.entities.MessageEntity;
import com.dd.mlm.topn.persistence.entities.NetworkNodeEntity;
import com.dd.mlm.topn.persistence.entities.ReferenceContentEntity;
import com.dd.mlm.topn.persistence.entities.TextContentEntity;
import com.dd.mlm.topn.persistence.entities.VideoContentEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(path = "/content-management")
public class ContentManagementService {
    
    @Autowired
    private ContainerContentRepository containerContentRepository;
    
    @Autowired
    private ContentRepository contentRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NetworkTreeRepository networkRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MailBoxRepository mailBoxRepository;
    
    private class ContentEntityFactory {
        
        private int index;
        
        private NetworkNodeEntity node;
        
        public ContentEntityFactory(int index, NetworkNodeEntity node) {
            this.index = index;
            this.node = node;
        }
        
        public ContentEntityFactory(NetworkNodeEntity node) {
            this.index = 0;
            this.node = node;
        }
        
        private void initialSetup(ContentEntity entity, String title, String value, ContainerContentEntity parent, UUID id) {
            entity.setContent(value);
            entity.setId(id);
            entity.setTitle(title);
            entity.setParent(parent);
            entity.setOrderIndex(index++);
            entity.setNode(node);
        }
        
        public TextContentEntity createText(String title, String value, ContainerContentEntity parent, UUID id) {
            TextContentEntity entity = new TextContentEntity();
            initialSetup(entity, title, value, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT);
            return contentRepository.save(entity);
        }
        
        public VideoContentEntity createVideo(String title, String value, ContainerContentEntity parent, UUID id) {
            VideoContentEntity entity = new VideoContentEntity();
            initialSetup(entity, title, value, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO);
            return contentRepository.save(entity);
        }
        
        private ImageContentEntity createImage(String title, String value, ContainerContentEntity parent, UUID id) {
            ImageContentEntity entity = new ImageContentEntity();
            initialSetup(entity, title, value, parent, id);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE);
            return contentRepository.save(entity);
        }
        
        private ReferenceContentEntity createReference(String title, String value, ContainerContentEntity parent, UUID id) {
            ReferenceContentEntity entity = new ReferenceContentEntity();
            initialSetup(entity, title, value, parent, id);

//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK);
            return contentRepository.save(entity);
        }
        
        private ContainerContentEntity createContent(String title, String value, ContainerContentEntity parent, int index, UUID id) {
            ContainerContentEntity entity = new ContainerContentEntity();
            entity.setContent(value);
            entity.setId(id);
            entity.setTitle(title);
            entity.setParent(parent);
            entity.setOrderIndex(index);
//            entity.setResourceType(ViewConstants.CONTENT_MANAGEMENT_WIDGET_CONTAINER);
            return contentRepository.save(entity);
        }
    }
//
//    private ContentEntityFactory factory = new ContentEntityFactory();

    @CrossOrigin
    @RequestMapping(path = "/page/{pageId}",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto page(@PathVariable(value = "pageId") String pageId) throws ContentNotFoundException {
        if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            PageDto dto = new PageDto();
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setTitle(container.getTitle());
            dto.setId(container.getId());
            if (ContainerContentEntity.RESOURCE_TYPE.equals(container.getResourceType())) {
                dto.setSections(contentRepository
                        .findByParent(UUID.fromString(pageId))
                        .stream()
                        .map((ContentEntity entity) -> new SectionDto(
                                entity.getId().toString(),
                                entity.getTitle(),
                                entity.getResourceType(),
                                entity.getContent(),
                                entity.isHasEmbeddedFile()))
                        .collect(Collectors.toList()));
            } else {
                LOG.log(Level.INFO, "{0} is not a container.", pageId);
            }
            return dto;
        }
    }
    
    @CrossOrigin
    @RequestMapping(path = "/toc/info",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto pageInfo(@RequestParam(value = "pageId", required = true) String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setTitle("");
            dto.setId(null);
            
        } else {
            
            UUID pageUuid = UUID.fromString(pageId);
            if (!contentRepository.exists(pageUuid)) {
                throw new ContentNotFoundException();
            } else {
                ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
            }
        }
        
        return dto;
        
    }
    
    private void publicArticles(String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setLeaf(false);
            dto.setHasEmbeddedFile(false);
            dto.setSections(contentRepository
                    .findPublicRoots()
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getResourceType(),
                            entity.getId().toString(),
                            entity.isHasEmbeddedFile()))
                    .collect(Collectors.toList()));
        } else if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setHasEmbeddedFile(container.isHasEmbeddedFile());
            if (container.isHasEmbeddedFile()) {
                dto.setEmbeddedFileName(container.getContent());
            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
                dto.setLeaf(container.getLeaf());
                dto.setSections(contentRepository
                        .findPublicByParent(UUID.fromString(pageId))
                        .stream()
                        .map((ContentEntity entity) -> new SectionDto(
                                entity.getId().toString(),
                                entity.getTitle(),
                                entity.getResourceType(),
                                entity.getLeaf() ? entity.getContent() : entity.getId().toString(),
                                entity.isHasEmbeddedFile()))
                        .collect(Collectors.toList()));
            }
            
        }
    }
    
    private void ownArticles(UUID accountId, String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setLeaf(false);
            dto.setHasEmbeddedFile(false);
            dto.setSections(contentRepository
                    .findDraftRoots(accountId)
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getResourceType(),
                            entity.getId().toString(),
                            entity.isHasEmbeddedFile()))
                    .collect(Collectors.toList()));
        } else if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setHasEmbeddedFile(container.isHasEmbeddedFile());
            if (container.isHasEmbeddedFile()) {
                dto.setEmbeddedFileName(container.getContent());
            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
                dto.setLeaf(container.getLeaf());
                dto.setSections(contentRepository
                        .findDraftByParent(accountId, UUID.fromString(pageId))
                        .stream()
                        .map((ContentEntity entity) -> new SectionDto(
                                entity.getId().toString(),
                                entity.getTitle(),
                                entity.getResourceType(),
                                entity.getLeaf() ? entity.getContent() : entity.getId().toString(),
                                entity.isHasEmbeddedFile()))
                        .collect(Collectors.toList()));
            }
            
        }
    }
    
    private void publishedArticles(String pageId, PageDto dto) throws ContentNotFoundException {
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setLeaf(false);
            dto.setHasEmbeddedFile(false);
            dto.setSections(contentRepository
                    .findPublishedRoots()
                    .stream()
                    .map((ContentEntity entity) -> new SectionDto(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getResourceType(),
                            entity.getId().toString(),
                            entity.isHasEmbeddedFile()))
                    .collect(Collectors.toList()));
        } else if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity container = contentRepository.findOne(UUID.fromString(pageId));
            dto.setHasEmbeddedFile(container.isHasEmbeddedFile());
            if (container.isHasEmbeddedFile()) {
                dto.setEmbeddedFileName(container.getContent());
            } else {
                dto.setTitle(container.getTitle());
                dto.setId(container.getId());
                dto.setLeaf(container.getLeaf());
                dto.setSections(contentRepository
                        .findPublishedByParent(UUID.fromString(pageId))
                        .stream()
                        .map((ContentEntity entity) -> new SectionDto(
                                entity.getId().toString(),
                                entity.getTitle(),
                                entity.getResourceType(),
                                entity.getLeaf() ? entity.getContent() : entity.getId().toString(),
                                entity.isHasEmbeddedFile()))
                        .collect(Collectors.toList()));
            }
            
        }
    }
    
    @CrossOrigin
    @RequestMapping(path = "/toc",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto toc(
            @RequestParam(value = "tag", defaultValue = "public", required = false) String tag,
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();
        switch (tag) {
            case "drafts":
                this.ownArticles(UUID.fromString(owner), pageId, dto);
                break;
            case "published":
                this.publishedArticles(pageId, dto);
                break;
            case "public":
            default:
                this.publicArticles(pageId, dto);
                break;
        }
        LOG.info("Fetched " + dto.getSections().size() + " articles from " + tag + " folder.");
        return dto;
    }
    
    @CrossOrigin
    @RequestMapping(path = "/toc/path",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public PageDto tocPath(@RequestParam(value = "pageId", defaultValue = "ROOT") String pageId) throws ContentNotFoundException {
        PageDto dto = new PageDto();
        dto.setSections(new ArrayList<>());
        if (ROOT_PSEUDO_ID.equals(pageId)) {
            dto.setTitle(ROOT_PLACEHOLDER);
            dto.getSections().add(new SectionDto(null, ROOT_PLACEHOLDER, null, "ROOT", false));
        } else if (!contentRepository.exists(UUID.fromString(pageId))) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity item = contentRepository.findOne(UUID.fromString(pageId));
//            dto.getSections().add(new SectionDto(item.getId().toString(), item.getTitle(), null, item.getId().toString()));
            while (item != null) {
                dto.getSections().add(new SectionDto(
                        item.getId().toString(),
                        item.getTitle(),
                        null,
                        item.getId().toString(),
                        item.isHasEmbeddedFile()));
                item = contentRepository.findByChild(item.getId());
                
            }
            dto.getSections().add(new SectionDto("ROOT", ROOT_PLACEHOLDER, null, "ROOT", false));
            Collections.reverse(dto.getSections());
        }
        return dto;
    }
    
    public static final String ROOT_PSEUDO_ID = "ROOT";
    
    @CrossOrigin
    @RequestMapping(path = "/{owner}/save",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void save(
            @PathVariable("owner") String owner,
            @RequestBody PageDto dto) {
        
        NetworkNodeEntity node = networkRepository.findByAccount(UUID.fromString(owner));
        
        ContentEntityFactory factory = new ContentEntityFactory(node);
        ContainerContentEntity container = factory.createContent(dto.getTitle(), null, null, 0, dto.getId());
        container.setLeaf(dto.isLeaf());
        container.setDraft(true);
        container.setNode(node);
        container.setHasEmbeddedFile(dto.isHasEmbeddedFile());
        if (container.isHasEmbeddedFile()) {
            container.setContent(dto.getEmbeddedFileName());
        } else {
            LOG.log(Level.INFO, "Created new assembled page[{0}].", container.getId());
            if (dto.getSections().isEmpty()) {
                dto.setLeaf(false);
            }
            dto.getSections().forEach(s -> {
                try {
                    ContentEntity ce = null;
                    UUID sectionId = null;
                    try {
                        sectionId = UUID.fromString(s.getName());
                        LOG.log(Level.INFO, "Section id presented, modify {0} section", s.getType());
                        ContentEntity sectionEntity = contentRepository.findOne(sectionId);
                        sectionEntity.setContent(s.getData());
                        sectionEntity.setTitle(s.getTitle());
                        sectionEntity.setNode(node);
                        contentRepository.save(sectionEntity);
                    } catch (IllegalArgumentException parseEx) {
                        LOG.log(Level.INFO, "Section id not presented, create {0} section", s.getType());
                        switch (s.getType()) {
                            case "video":
                            case ViewConstants.CONTENT_MANAGEMENT_WIDGET_VIDEO:
                                
                                ce = factory.createVideo(s.getTitle(), s.getData(), container, sectionId);
                                break;
                            case "image":
                            case ViewConstants.CONTENT_MANAGEMENT_WIDGET_IMAGE:
                                ce = factory.createImage(s.getTitle(), s.getData(), container, sectionId);
                                break;
                            case "link":
                            case ViewConstants.CONTENT_MANAGEMENT_WIDGET_LINK:
                                ce = factory.createReference(s.getTitle(), s.getData(), container, sectionId);
                                dto.setLeaf(false);
                                break;
                            case "text":
                            case ViewConstants.CONTENT_MANAGEMENT_WIDGET_TEXT:
                            default:
                                ce = factory.createText(s.getTitle(), s.getData(), container, sectionId);
                                break;
                        }
                        
                        LOG.log(Level.INFO, "Created new {0}-content[{1}].", new Object[]{s.getType(), ce.getId()});
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Failed to persist section.", ex);
                }
                
            });
        }
    }
    
    @CrossOrigin
    @RequestMapping(path = "/publish",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public void publish(MessageDto dto) throws ContentNotFoundException {
        if (!contentRepository.exists(dto.getId())) {
            throw new ContentNotFoundException();
        } else {
            ContentEntity contentEntity = contentRepository.findOne(dto.getId());
            messageRepository.save(dto.getRecipients().stream().map((UUID recipientId) -> {
                MessageEntity message = new MessageEntity();
                message.setContent(contentEntity);
                message.setMailBox(mailBoxRepository.findByRecipient(recipientId));
                message.setRead(false);
                return message;
            }).collect(Collectors.toList()));
        }
    }
    
    @CrossOrigin
    @RequestMapping(path = "/candidates/parent",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public List<PageDto> parentCandidates() {
        List<PageDto> pages = containerContentRepository.findAll()
                .stream()
                .filter(e -> !e.getLeaf())
                .map((ContainerContentEntity e) -> new PageDto(e.getId(), e.getTitle()))
                .collect(Collectors.toList());
        pages.add(new PageDto(null, ROOT_PLACEHOLDER));
        return pages;
    }
    
    @CrossOrigin
    @RequestMapping(path = "/candidates/child",
            method = RequestMethod.GET,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
    public List<PageDto> childCandidates() {
        List<PageDto> pages = containerContentRepository.findAll()
                .stream()
                .filter(e -> e.getLeaf())
                .map((ContainerContentEntity e) -> new PageDto(e.getId(), e.getTitle()))
                .collect(Collectors.toList());
        return pages;
    }
    
    private static final String ROOT_PLACEHOLDER = "Root";
    
    private static final Logger LOG = Logger.getLogger(ContentManagementService.class.getName());
}
