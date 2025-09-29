package org.hl.wirtualnyregalbackend.publisher;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherPageResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherRequest;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/publishers")
@AllArgsConstructor
class PublisherController {

    private final PublisherCommandService command;
    private final PublisherQueryService query;


    @PostMapping
    public ResponseEntity<PublisherResponse> createPublisher(@RequestBody
                                                             @Validated(CreateGroup.class)
                                                             PublisherRequest publisherRequest,
                                                             UriComponentsBuilder uriBuilder) {
        PublisherResponse response = command.createPublisher(publisherRequest);

        URI location = uriBuilder
            .path("/v1/books/{id}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{publisherId}")
    public PublisherDetailsResponse findPublisherDetailsById(@PathVariable Long publisherId) {
        return query.findPublisherDetailsById(publisherId);
    }

    @GetMapping
    public PublisherPageResponse findPublishers(Pageable pageable) {
        return query.findPublishers(pageable);
    }

}
