package org.hl.wirtualnyregalbackend.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(List<String> excludedPaths) {
}
