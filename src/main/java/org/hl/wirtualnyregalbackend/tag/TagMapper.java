package org.hl.wirtualnyregalbackend.tag;

import org.hl.wirtualnyregalbackend.tag.dto.TagDto;
import org.hl.wirtualnyregalbackend.tag.entity.Tag;

class TagMapper {

    private TagMapper() {
    }

    public static TagDto toTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public static Tag toTag(TagDto tagDto) {
        return new Tag(tagDto.name());
    }

}
