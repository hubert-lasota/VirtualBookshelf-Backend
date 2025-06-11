package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

public class PublisherMapper {

    private PublisherMapper() {
    }

    public static PublisherMutationDto toPublisherMutationDto(Publisher publisher) {
        return new PublisherMutationDto(publisher.getName());
    }

    public static PublisherResponseDto toPublisherResponseDto(Publisher publisher) {
        PublisherMutationDto dto = toPublisherMutationDto(publisher);
        return new PublisherResponseDto(publisher.getId(), publisher.getCreatedAt(), publisher.getUpdatedAt(), dto);
    }

    public static Publisher toPublisher(PublisherMutationDto publisherDto) {
        return new Publisher(publisherDto.name());
    }

}
