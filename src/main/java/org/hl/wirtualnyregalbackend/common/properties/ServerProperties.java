package org.hl.wirtualnyregalbackend.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record ServerProperties(String protocol, String host, String port) {

    public ServerProperties(
        @Value("${app.protocol}") String protocol,
        @Value("${app.host}") String host,
        @Value("${server.port}") String port
    ) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public String origin() {
        return protocol + "://" + host + ":" + port;
    }

}