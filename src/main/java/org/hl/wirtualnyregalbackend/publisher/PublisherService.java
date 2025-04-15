package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.dao.PublisherRepository;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    public PublisherDto createPublisher(PublisherDto publisherDto) {
        boolean exists = publisherRepository.existsByNameIgnoreCase(publisherDto.name());
        if (exists) {
            throw new InvalidRequestException( "Publisher with %s name already exists".formatted(publisherDto.name()));
        }
        Publisher publisher = PublisherMapper.toPublisher(publisherDto);
        publisherRepository.save(publisher);
        return PublisherMapper.toPublisherDto(publisher);
    }

    public Set<Publisher> findAndCreatePublishers(Collection<String> publisherNames) {
        List<Publisher> existingPublishers = publisherRepository.findByNamesIgnoreCase(publisherNames);
        List<String> existingNames = existingPublishers
                .stream().map(Publisher::getName)
                .toList();

        List<Publisher> newPublishers = publisherNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(Publisher::new)
                .toList();

        newPublishers = publisherRepository.saveAll(newPublishers);

        Set<Publisher> publishers = new HashSet<>(existingPublishers);
        publishers.addAll(newPublishers);
        return publishers;
    }

    public boolean existsByNameIgnoreCase(String publisherName) {
        return publisherRepository.existsByNameIgnoreCase(publisherName);
    }

}
