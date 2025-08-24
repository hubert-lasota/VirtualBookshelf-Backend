package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import java.util.List;

public record MonthlyStatisticsListResponse(List<MonthlyStatisticsResponse> monthlyStatistics) {
}
