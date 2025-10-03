package org.hl.wirtualnyregalbackend.book;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.recommendation.book-scores-multiplier")
public record BookScoresMultiplierProperties(
    double book,
    double author,
    double genre
) {
}
