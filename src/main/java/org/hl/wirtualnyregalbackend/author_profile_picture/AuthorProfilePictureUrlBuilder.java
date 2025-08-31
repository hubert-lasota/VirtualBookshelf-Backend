package org.hl.wirtualnyregalbackend.author_profile_picture;

import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.common.properties.ServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class AuthorProfilePictureUrlBuilder {

    private final ServerProperties serverProperties;
    private final String urlPrefix;

    public AuthorProfilePictureUrlBuilder(ServerProperties serverProperties,
                                          @Value("${app.author-profile-picture.url-prefix}") String urlPrefix) {
        this.serverProperties = serverProperties;
        this.urlPrefix = urlPrefix;
    }

    public String buildPictureUrl(AuthorProfilePicture picture) {
        return serverProperties.origin() + urlPrefix + picture.getId();
    }

}
