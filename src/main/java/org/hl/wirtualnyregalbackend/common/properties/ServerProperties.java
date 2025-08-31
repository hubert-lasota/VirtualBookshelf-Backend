package org.hl.wirtualnyregalbackend.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record ServerProperties(String protocol, String address, String port) {

    public ServerProperties(
        @Value("${app.protocol}") String protocol,
        @Value("${server.address}") String address,
        @Value("${server.port}") String port
    ) {
        this.protocol = protocol;
        this.address = address;
        this.port = port;
    }

    public String origin() {
        return protocol + "://" + address + ":" + port;
    }

}