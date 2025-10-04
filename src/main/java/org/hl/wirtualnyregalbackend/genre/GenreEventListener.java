package org.hl.wirtualnyregalbackend.genre;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.event.BookGenreUpdatedEvent;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
class GenreEventListener {

    @Transactional
    @EventListener
    public void on(BookGenreUpdatedEvent event) {
        Set<Genre> diff = new HashSet<>(event.newGenres());
        diff.removeAll(event.oldGenres());
        diff.forEach(Genre::incrementTotalBooks);
    }

}
