package org.hl.wirtualnyregalbackend.publisher;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherDetailsResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherPageResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
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

    private final PublisherService publisherService;


    @PostMapping
    public ResponseEntity<PublisherResponseDto> createPublisher(@RequestBody
                                                                @Validated(CreateGroup.class)
                                                                PublisherMutationDto publisherDto,
                                                                UriComponentsBuilder uriBuilder) {
        PublisherResponseDto response = publisherService.createPublisher(publisherDto);

        URI location = uriBuilder
            .path("/v1/books/{id}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{publisherId}")
    public PublisherDetailsResponseDto findPublisherDetailsById(@PathVariable Long publisherId) {
        return publisherService.findPublisherDetailsById(publisherId);
    }

    @GetMapping
    public PublisherPageResponseDto findPublishers(Pageable pageable) {
        return publisherService.findPublishers(pageable);
    }

}
