package org.hl.wirtualnyregalbackend.tag;

import org.hl.wirtualnyregalbackend.tag.model.Tag;
import org.hl.wirtualnyregalbackend.tag.model.dto.TagDto;

public class TagMapper {

    private TagMapper() { }

    public static TagDto toTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public static Tag toTag(TagDto tagDto) {
        return new Tag(tagDto.name());
    }

}
