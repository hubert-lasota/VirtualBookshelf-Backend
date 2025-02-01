package org.hl.wirtualnyregalbackend.infrastructure.tag;

import org.hl.wirtualnyregalbackend.application.tag.Tag;

import java.util.List;

public interface TagRepository {

    Tag save(Tag tag);

    void saveBookTag(String tagName, Long bookId);

    void saveBookTag(String tagName, String bookExternalApiId);

    void saveAuthorTag(String tagName, Long authorId);

    void saveAuthorTag(String tagName, String authorExternalApiId);

    List<Tag> findBookTags(Long bookId);

    List<Tag> findBookTags(String bookExternalApiId);

    List<Tag> findAuthorTags(Long authorId);

    List<Tag> findAuthorTags(String authorExternalApiId);

    boolean existsByNameIgnoreCase(String name);

}
