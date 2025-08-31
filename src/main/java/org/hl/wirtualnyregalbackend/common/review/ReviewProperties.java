package org.hl.wirtualnyregalbackend.common.review;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.review")
record ReviewProperties(List<Float> validRatings) {
}
