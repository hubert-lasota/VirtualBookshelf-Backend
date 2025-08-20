package org.hl.wirtualnyregalbackend.genre;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.genre.dto.GenrePageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/genres")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GenreController {

    private final GenreService genreService;


    @GetMapping
    public GenrePageResponse findGenres(Pageable pageable) {
        return genreService.findGenres(pageable);
    }

}
