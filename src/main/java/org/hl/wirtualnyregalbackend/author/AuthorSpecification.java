package org.hl.wirtualnyregalbackend.author;

import jakarta.persistence.criteria.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.model.AuthorFilter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.recommendation.entity.AuthorRecommendation;
import org.springframework.data.jpa.domain.Specification;

class AuthorSpecification {

    private AuthorSpecification() {
    }

    public static Specification<Author> sortByRecommendation(User user) {
        return (root, query, cb) -> {
            Subquery<Float> scoreSub = query.subquery(Float.class);
            Root<AuthorRecommendation> ar = scoreSub.from(AuthorRecommendation.class);
            scoreSub.select(cb.sum(ar.get("score")));
            scoreSub.where(
                cb.equal(ar.get("author"), root),
                cb.equal(ar.get("user"), user)
            );

            Expression<Double> finalScore = cb.coalesce(scoreSub.getSelection(), 0.0).as(Double.class);

            query.orderBy(cb.desc(finalScore));
            return cb.conjunction();
        };
    }

    public static Specification<Author> byFilterAndUser(AuthorFilter filter, User user) {
        Specification<Author> spec = Specification.where(null);
        if (filter.availableInBookshelf() != null) {
            spec = byAvailableInBookshelf(filter.availableInBookshelf(), user);
        }

        if (filter.query() != null) {
            spec = spec.and(byQuery(filter.query()));
        }

        return spec;
    }

    private static Specification<Author> byAvailableInBookshelf(boolean availableInBookshelf, User user) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ReadingBook> rbRoot = subquery.from(ReadingBook.class);
            Join<ReadingBook, Book> bookJoin = rbRoot.join("book");
            Join<Book, Author> authorJoin = bookJoin.join("authors");
            Join<ReadingBook, Bookshelf> bookshelfJoin = rbRoot.join("bookshelf");

            subquery
                .select(authorJoin.get("id"))
                .where(cb.equal(bookshelfJoin.get("user"), user))
                .distinct(true);

            Predicate predicate = root.get("id").in(subquery);
            return availableInBookshelf ? predicate : cb.not(predicate);
        };
    }

    private static Specification<Author> byQuery(String query) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get("fullName")), "%" + query.toLowerCase() + "%");
    }

}
