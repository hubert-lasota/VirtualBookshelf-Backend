package org.hl.wirtualnyregalbackend.book_series;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book-series")
@AllArgsConstructor
class BookSeriesController {

    private final BookSeriesService bookSeriesService;


    @GetMapping
    public ResponseEntity<?> findBookSeries(Pageable pageable) {
        var response = bookSeriesService.findBookSeries(pageable);
        return ResponseEntity.ok(response);
    }

}
