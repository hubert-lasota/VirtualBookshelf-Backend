package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/publishers")
class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }


    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody @Validated(CreateGroup.class) PublisherMutationDto publisherDto) {
        PublisherResponseDto response = publisherService.createPublisher(publisherDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findPublishers(Pageable pageable) {
        PageResponseDto<PublisherResponseDto> response = publisherService.findPublishers(pageable);
        return ResponseEntity.ok(response);
    }

}
