package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

public class PublisherMapper {

    private PublisherMapper() {
    }


    public static PublisherResponseDto toPublisherResponseDto(Publisher publisher) {
        return new PublisherResponseDto(publisher.getId(), publisher.getName());
    }

    public static PublisherDetailsResponseDto toPublisherDetailsResponseDto(Publisher publisher) {
        return new PublisherDetailsResponseDto(
            publisher.getId(),
            publisher.getName(),
            publisher.getCreatedAt(),
            publisher.getUpdatedAt()
        );
    }

    public static Publisher toPublisher(PublisherMutationDto publisherDto) {
        return new Publisher(publisherDto.getName());
    }

}
