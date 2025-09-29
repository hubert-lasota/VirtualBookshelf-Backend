package org.hl.wirtualnyregalbackend.genre;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.dto.GenreListResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/genres")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GenreController {

    private final GenreQueryService query;


    @GetMapping
    public GenreListResponse findGenres(@RequestParam(required = false) Boolean availableInBookshelf,
                                        @AuthenticationPrincipal User user) {
        return query.findGenres(availableInBookshelf, user);
    }

}
