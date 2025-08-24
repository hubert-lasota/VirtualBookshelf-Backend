package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/statistics")
class ReadingStatisticsController {

    private final ReadingStatisticsService statsService;


}
