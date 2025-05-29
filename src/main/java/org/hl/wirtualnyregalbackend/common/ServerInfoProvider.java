package org.hl.wirtualnyregalbackend.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerInfoProvider {

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    @Value("${app.protocol}")
    private String protocol;


    public String getOrigin() {
        return protocol + "://" + address + ":" + port;
    }
}
