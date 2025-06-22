package org.hl.wirtualnyregalbackend.publisher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PublisherService {

    private final PublisherRepository publisherRepository;


    public PublisherResponseDto createPublisher(PublisherMutationDto publisherDto) {
        Publisher publisher = createPublisherEntity(publisherDto);
        return PublisherMapper.toPublisherResponseDto(publisher);
    }

    public PageResponseDto<PublisherResponseDto> findPublishers(Pageable pageable) {
        Page<Publisher> publishers = publisherRepository.findAll(pageable);
        Page<PublisherResponseDto> publisherDtos = publishers.map(PublisherMapper::toPublisherResponseDto);
        return new PageResponseDto<>(publisherDtos, "publishers");
    }

    public Publisher findOrCreatePublisher(Long id, PublisherMutationDto publisherDto) {
        if (id != null) {
            return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher with id='%d' not found".formatted(id)));
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

}
