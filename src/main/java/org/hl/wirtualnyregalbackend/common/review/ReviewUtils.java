package org.hl.wirtualnyregalbackend.common.review;

import java.util.List;

public class ReviewUtils {

    private ReviewUtils() {
    }

    public static List<ReviewStatistics> roundAverage(List<ReviewStatistics> reviewStats) {
        return reviewStats.stream()
            .map(stats -> {
                Double roundedAvg = stats.average() != null
                    ? Math.round(stats.average() * 10.0) / 10.0
                    : 0.0;
                return new ReviewStatistics(stats.entityId(), roundedAvg, stats.total());
            })
            .toList();
    }
}
