package org.hl.wirtualnyregalbackend.application.publisher;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.application.common.ApiError;
import org.hl.wirtualnyregalbackend.application.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.infrastructure.publisher.PublisherRepository;
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


    public Publisher createPublisher(String publisherName) {
        publisherName = publisherName.strip();
        boolean exists = publisherRepository.existsByNameIgnoreCase(publisherName);
        if (exists) {
            ApiError error = new ApiError("name", "Publisher with name='%s' already exists".formatted(publisherName));
            throw new InvalidRequestException(List.of(error), ActionType.CREATE, "Publisher with %s name already exists".formatted(publisherName));
        }
        Publisher publisher = new Publisher(publisherName);
        return publisherRepository.save(publisher);
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
