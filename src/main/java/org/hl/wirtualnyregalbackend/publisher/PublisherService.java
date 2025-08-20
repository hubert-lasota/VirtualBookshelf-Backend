package org.hl.wirtualnyregalbackend.publisher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherPageResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherRequest;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PublisherService {

    private final PublisherRepository publisherRepository;


    public PublisherResponse createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = createPublisherEntity(publisherRequest);
        return PublisherMapper.toPublisherResponseDto(publisher);
    }

    public PublisherPageResponse findPublishers(Pageable pageable) {
        Page<PublisherResponse> page = publisherRepository
            .findAll(pageable)
            .map(PublisherMapper::toPublisherResponseDto);
        return PublisherPageResponse.from(page);
    }

    public PublisherDetailsResponse findPublisherDetailsById(Long publisherId) {
        Publisher publisher = findPublisherEntityById(publisherId);
        return PublisherMapper.toPublisherDetailsResponseDto(publisher);
    }

    public Publisher findOrCreatePublisher(Long id, PublisherRequest publisherDto) {
        if (id != null) {
            return findPublisherEntityById(id);
        }
        return createPublisherEntity(publisherDto);
    }

    private Publisher createPublisherEntity(PublisherRequest publisherRequest) {
        String name = publisherRequest.name();
        boolean exists = publisherRepository.existsByNameIgnoreCase(name);
        if (exists) {
            throw new InvalidRequestException("Publisher with %s name already exists".formatted(name));
        }
        Publisher publisher = PublisherMapper.toPublisher(publisherRequest);
        return publisherRepository.save(publisher);
    }

    private Publisher findPublisherEntityById(Long id) {
        Optional<Publisher> publisherOpt = id != null ? publisherRepository.findById(id) : Optional.empty();
        return publisherOpt.orElseThrow(() -> new EntityNotFoundException("Publisher with id='%d' not found".formatted(id)));
    }
}
