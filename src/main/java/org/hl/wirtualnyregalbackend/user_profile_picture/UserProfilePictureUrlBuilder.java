package org.hl.wirtualnyregalbackend.user_profile_picture;

import org.hl.wirtualnyregalbackend.common.properties.ServerProperties;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class UserProfilePictureUrlBuilder {

    private final ServerProperties serverProperties;
    private final String urlPrefix;

    public UserProfilePictureUrlBuilder(ServerProperties serverProperties,
                                        @Value("${app.user-profile-picture.url-prefix}") String urlPrefix) {
        this.serverProperties = serverProperties;
        this.urlPrefix = urlPrefix;
    }

    public String buildProfilePictureUrl(UserProfilePicture profilePicture) {
        return serverProperties.origin() + urlPrefix + profilePicture.getId();
    }

}
