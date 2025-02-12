package org.hl.wirtualnyregalbackend.tag;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.common.InvalidRequestException;

import java.util.List;

public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public String createBookTag(String name, Book book) {
        if(!tagRepository.existsByNameIgnoreCase(name)) {
            Tag tag = new Tag(name);
            tagRepository.save(tag);
        }
        if(book.getId() != null) {
            tagRepository.saveBookTag(name, book.getId());
        } else {
            tagRepository.saveBookTag(name, book.getExternalApiId());
        }
        return name;
    }

    public String createAuthorTag(String name, Author author) {
        if(!tagRepository.existsByNameIgnoreCase(name)) {
            Tag tag = new Tag(name);
            tagRepository.save(tag);
        }
        if(author.getId() != null) {
            tagRepository.saveAuthorTag(name, author.getId());
        } else {
            tagRepository.saveAuthorTag(name, author.getExternalApiId());
        }
        return name;
    }

    public String createTag(String name) {
        name = name.strip();
        if(tagRepository.existsByNameIgnoreCase(name)) {
            throw new InvalidRequestException(null, ActionType.CREATE, "Tag with name=%s already exists.".formatted(name));
        }
        Tag tag = new Tag(name);
        tagRepository.save(tag);
        return name;
    }

    public List<Tag> findBookTags(Long bookId) {
        return tagRepository.findBookTags(bookId);
    }

    public List<Tag> findBookTags(String bookExternalApiId) {
        return tagRepository.findBookTags(bookExternalApiId);
    }

    public List<Tag> findAuthorTags(Long authorId) {
        return tagRepository.findAuthorTags(authorId);
    }

    public List<Tag> findAuthorTags(String authorExternalApiId) {
        return tagRepository.findAuthorTags(authorExternalApiId);
    }

}
