package org.hl.wirtualnyregalbackend.tag.dao;

import org.hl.wirtualnyregalbackend.tag.model.Tag;
import org.hl.wirtualnyregalbackend.tag.model.TagFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepository {

    Tag save(Tag tag);

    void saveBookTag(String tagName, Long bookId);

    void saveAuthorTag(String tagName, Long authorId);

    Page<Tag> findByTagFilter(TagFilter tagFilter, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

}
