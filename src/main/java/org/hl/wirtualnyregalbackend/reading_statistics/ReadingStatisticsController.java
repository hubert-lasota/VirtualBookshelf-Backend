package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.MonthlyStatisticsListResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/statistics")
class ReadingStatisticsController {

    private final ReadingStatisticsService statsService;

    @GetMapping("/monthly")
    public MonthlyStatisticsListResponse findMonthlyStatistics(@AuthenticationPrincipal User user) {
        return statsService.findMonthlyStatistics(user);
    }

}
