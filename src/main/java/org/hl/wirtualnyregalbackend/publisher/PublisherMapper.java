package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherRequest;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

public class PublisherMapper {

    private PublisherMapper() {
    }


    public static PublisherResponse toPublisherResponseDto(Publisher publisher) {
        return new PublisherResponse(publisher.getId(), publisher.getName());
    }

    public static PublisherDetailsResponse toPublisherDetailsResponseDto(Publisher publisher) {
        return new PublisherDetailsResponse(
            publisher.getId(),
            publisher.getName(),
            publisher.getCreatedAt(),
            publisher.getUpdatedAt()
        );
    }

    public static Publisher toPublisher(PublisherRequest publisherDto) {
        return new Publisher(publisherDto.name());
    }

}
