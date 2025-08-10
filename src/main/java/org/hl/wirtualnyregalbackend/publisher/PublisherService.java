package org.hl.wirtualnyregalbackend.publisher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherPageResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PublisherService {

    private final PublisherRepository publisherRepository;


    public PublisherResponseDto createPublisher(PublisherMutationDto publisherDto) {
        Publisher publisher = createPublisherEntity(publisherDto);
        return PublisherMapper.toPublisherResponseDto(publisher);
    }

    public PublisherPageResponseDto findPublishers(Pageable pageable) {
        Page<PublisherResponseDto> page = publisherRepository
            .findAll(pageable)
            .map(PublisherMapper::toPublisherResponseDto);
        return PublisherPageResponseDto.from(page);
    }

    public PublisherDetailsResponseDto findPublisherDetailsById(Long publisherId) {
        Publisher publisher = findPublisherEntityById(publisherId);
        return PublisherMapper.toPublisherDetailsResponseDto(publisher);
    }

    public Publisher findOrCreatePublisher(Long id, PublisherMutationDto publisherDto) {
        if (id != null) {
            return findPublisherEntityById(id);
        }
        return createPublisherEntity(publisherDto);
    }

    private Publisher createPublisherEntity(PublisherMutationDto publisherDto) {
        String name = publisherDto.getName();
        boolean exists = publisherRepository.existsByNameIgnoreCase(name);
        if (exists) {
            throw new InvalidRequestException("Publisher with %s name already exists".formatted(name));
        }
        Publisher publisher = PublisherMapper.toPublisher(publisherDto);
        return publisherRepository.save(publisher);
    }

    private Publisher findPublisherEntityById(Long id) {
        Optional<Publisher> publisherOpt = id != null ? publisherRepository.findById(id) : Optional.empty();
        return publisherOpt.orElseThrow(() -> new EntityNotFoundException("Publisher with id='%d' not found".formatted(id)));
    }
}
