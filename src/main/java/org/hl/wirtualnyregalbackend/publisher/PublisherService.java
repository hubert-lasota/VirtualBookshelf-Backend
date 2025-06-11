package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    public PublisherResponseDto createPublisher(PublisherMutationDto publisherDto) {
        Publisher publisher = createPublisherEntity(publisherDto);
        return PublisherMapper.toPublisherResponseDto(publisher);
    }

    public Publisher findOrCreatePublisher(Long id, PublisherMutationDto publisherDto) {
        if (id != null) {
            return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher with id='%d' not found".formatted(id)));
        }
        return createPublisherEntity(publisherDto);
    }

    private Publisher createPublisherEntity(PublisherMutationDto publisherDto) {
        boolean exists = publisherRepository.existsByNameIgnoreCase(publisherDto.name());
        if (exists) {
            throw new InvalidRequestException("Publisher with %s name already exists".formatted(publisherDto.name()));
        }
        Publisher publisher = PublisherMapper.toPublisher(publisherDto);
        return publisherRepository.save(publisher);
    }

}
