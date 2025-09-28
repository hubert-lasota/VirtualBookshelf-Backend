package org.hl.wirtualnyregalbackend.genre;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.domain.Specification;

class GenreSpecification {

    private GenreSpecification() {
    }

    public static Specification<Genre> byAvailableInBookshelf(boolean availableInBookshelf, User user) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ReadingBook> rb = subquery.from(ReadingBook.class);
            Join<ReadingBook, Book> b = rb.join("book");
            Join<Book, Genre> g = b.join("genres");

            subquery
                .select(g.get("id"))
                .where(cb.equal(rb.get("bookshelf").get("user"), user))
                .distinct(true);

            Predicate predicate = root.get("id").in(subquery);
            return availableInBookshelf ? predicate : cb.not(predicate);
        };
    }

}
