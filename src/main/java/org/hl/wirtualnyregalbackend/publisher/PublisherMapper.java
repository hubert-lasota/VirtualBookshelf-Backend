package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

public class PublisherMapper {

    private PublisherMapper() { }

    public static PublisherDto toPublisherDto(Publisher publisher) {
        return new PublisherDto(publisher.getId(), publisher.getName());
    }

    public static Publisher toPublisher(PublisherDto publisherDto) {
        return new Publisher(publisherDto.name());
    }
}
