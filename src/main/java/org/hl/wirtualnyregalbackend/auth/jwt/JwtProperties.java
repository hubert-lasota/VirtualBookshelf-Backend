package org.hl.wirtualnyregalbackend.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app.security.jwt")
record JwtProperties(String secretKey, int expirationDays) {
}