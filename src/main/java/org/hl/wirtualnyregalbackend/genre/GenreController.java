package org.hl.wirtualnyregalbackend.genre;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/genres")
class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<?> findGenres() {
        List<GenreResponseDto> genres = genreService.findGenres();
        Map<String, List<GenreResponseDto>> response = Map.of("genres", genres);
        return ResponseEntity.ok(response);
    }

}
