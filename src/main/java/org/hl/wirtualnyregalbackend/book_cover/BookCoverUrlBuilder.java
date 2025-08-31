package org.hl.wirtualnyregalbackend.book_cover;

import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.common.properties.ServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class BookCoverUrlBuilder {

    private final ServerProperties serverProperties;
    private final String urlPrefix;

    public BookCoverUrlBuilder(ServerProperties serverProperties,
                               @Value("${app.book-cover.url-prefix}") String urlPrefix) {
        this.serverProperties = serverProperties;
        this.urlPrefix = urlPrefix;
    }

    public String buildCoverUrl(BookCover cover) {
        return serverProperties.origin() + urlPrefix + cover.getId();
    }

}
