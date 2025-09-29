package org.hl.wirtualnyregalbackend.publisher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherRequest;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class PublisherCommandService {

    private final PublisherRepository repository;
    private final PublisherQueryService query;


    @Transactional
    public PublisherResponse createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = createPublisherEntity(publisherRequest);
        log.info("Publisher created with ID: {}", publisher.getId());
        return PublisherMapper.toPublisherResponse(publisher);
    }

    @Transactional
    public Publisher findOrCreatePublisher(Long id, PublisherRequest publisherDto) {
        if (id != null) {
            return query.findPublisherById(id);
        }
        return createPublisherEntity(publisherDto);
    }

    @Transactional
    public Publisher createPublisherEntity(PublisherRequest publisherRequest) {
        Publisher publisher = PublisherMapper.toPublisher(publisherRequest);
        return repository.save(publisher);
    }

}
