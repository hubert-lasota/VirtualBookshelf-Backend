package org.hl.wirtualnyregalbackend.genre;

import jakarta.persistence.criteria.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.genre.model.GenreFilter;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.recommendation.entity.GenreRecommendation;
import org.springframework.data.jpa.domain.Specification;

class GenreSpecification {

    private GenreSpecification() {
    }


    public static Specification<Genre> sortByRecommendation(User user) {
        return (root, query, cb) -> {
            Subquery<Float> scoreSub = query.subquery(Float.class);
            Root<GenreRecommendation> gr = scoreSub.from(GenreRecommendation.class);
            scoreSub.select(cb.sum(gr.get("score")));
            scoreSub.where(
                cb.equal(gr.get("genre"), root),
                cb.equal(gr.get("user"), user)
            );

            Expression<Double> finalScore = cb.coalesce(scoreSub.getSelection(), 0.0).as(Double.class);

            query.orderBy(cb.desc(finalScore));
            return cb.conjunction();
        };
    }

    public static Specification<Genre> byFilterAndUser(GenreFilter filter, User user) {
        Specification<Genre> spec = Specification.where(null);
        if (filter.availableInBookshelf() != null) {
            spec = byAvailableInBookshelf(filter.availableInBookshelf(), user);
        }

        return spec;
    }

    private static Specification<Genre> byAvailableInBookshelf(boolean availableInBookshelf, User user) {
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
