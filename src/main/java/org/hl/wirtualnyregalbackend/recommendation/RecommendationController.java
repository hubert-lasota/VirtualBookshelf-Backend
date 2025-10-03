package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.AuthorQueryService;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponse;
import org.hl.wirtualnyregalbackend.author.model.AuthorFilter;
import org.hl.wirtualnyregalbackend.book.BookQueryService;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.genre.GenreQueryService;
import org.hl.wirtualnyregalbackend.genre.dto.GenreListResponse;
import org.hl.wirtualnyregalbackend.genre.model.GenreFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/recommendation")
@AllArgsConstructor
class RecommendationController {

    private final BookQueryService bookQuery;
    private final AuthorQueryService authorQuery;
    private final GenreQueryService genreQuery;


    @GetMapping("/books")
    public BookPageResponse findBooks(@AuthenticationPrincipal User user, BookFilter filter, Pageable pageable) {
        return bookQuery.findRecommendedBooks(user, filter, pageable);
    }

    @GetMapping("/authors")
    public AuthorPageResponse findAuthors(@AuthenticationPrincipal User user, AuthorFilter filter, Pageable pageable) {
        return authorQuery.findRecommendedAuthors(user, filter, pageable);
    }

    @GetMapping("/genres")
    public GenreListResponse findGenres(@AuthenticationPrincipal User user, GenreFilter filter) {
        return genreQuery.findRecommendedGenres(filter, user);
    }

}
