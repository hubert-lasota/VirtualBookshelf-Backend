package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.dao.PublisherRepository;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    public PublisherDto createPublisher(PublisherDto publisherDto) {
        Publisher publisher = createPublisherEntity(publisherDto);
        return PublisherMapper.toPublisherDto(publisher);
    }

    public Publisher findOrCreatePublisher(PublisherDto publisherDto) {
        Optional<Publisher> publisherOpt = publisherRepository.findById(publisherDto.id());
        return publisherOpt.orElseGet(() -> createPublisherEntity(publisherDto));
    }

    private Publisher createPublisherEntity(PublisherDto publisherDto) {
        boolean exists = publisherRepository.existsByNameIgnoreCase(publisherDto.name());
        if (exists) {
            throw new InvalidRequestException("Publisher with %s name already exists".formatted(publisherDto.name()));
        }
        Publisher publisher = PublisherMapper.toPublisher(publisherDto);
        return publisherRepository.save(publisher);
    }

}
