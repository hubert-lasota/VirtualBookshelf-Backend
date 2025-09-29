package org.hl.wirtualnyregalbackend.publisher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherPageResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.hl.wirtualnyregalbackend.publisher.exception.PublisherNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class PublisherQueryService {

    private final PublisherRepository repository;


    public PublisherPageResponse findPublishers(Pageable pageable) {
        Page<PublisherResponse> page = repository
            .findAll(pageable)
            .map(PublisherMapper::toPublisherResponse);
        return PublisherPageResponse.from(page);
    }

    public PublisherDetailsResponse findPublisherDetailsById(Long publisherId) {
        Publisher publisher = findPublisherById(publisherId);
        return PublisherMapper.toPublisherDetailsResponse(publisher);
    }

    public Publisher findPublisherById(Long id) throws PublisherNotFoundException {
        Optional<Publisher> publisherOpt = id != null ? repository.findById(id) : Optional.empty();
        return publisherOpt.orElseThrow(() -> {
            log.warn("Publisher not found with ID: {}", id);
            return new PublisherNotFoundException(id);
        });
    }
}
