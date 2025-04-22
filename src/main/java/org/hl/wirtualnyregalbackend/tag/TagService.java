package org.hl.wirtualnyregalbackend.tag;

import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.tag.dao.TagRepository;
import org.hl.wirtualnyregalbackend.tag.model.Tag;
import org.hl.wirtualnyregalbackend.tag.model.dto.TagDto;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    //    public String createBookTag(String name, Book book) {
//        if(!tagRepository.existsByNameIgnoreCase(name)) {
//            Tag tag = new Tag(name);
//            tagRepository.save(tag);
//        }
//        if(book.getId() != null) {
//            tagRepository.saveBookTag(name, book.getId());
//        } else {
//            tagRepository.saveBookTag(name, book.getExternalApiId());
//        }
//        return name;
//    }
//
//    public String createAuthorTag(String name, Author author) {
//        if(!tagRepository.existsByNameIgnoreCase(name)) {
//            Tag tag = new Tag(name);
//            tagRepository.save(tag);
//        }
//        if(author.getId() != null) {
//            tagRepository.saveAuthorTag(name, author.getId());
//        } else {
//            tagRepository.saveAuthorTag(name, author.getExternalApiId());
//        }
//        return name;
//    }
//
    public TagDto createTag(TagDto tagDto) {
        if (tagRepository.existsByNameIgnoreCase(tagDto.name())) {
            throw new InvalidRequestException("Tag with name=%s already exists.".formatted(tagDto.name()));
        }
        Tag tag = TagMapper.toTag(tagDto);
        tagRepository.save(tag);
        return TagMapper.toTagDto(tag);
    }

}
