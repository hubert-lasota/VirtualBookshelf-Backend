package org.hl.wirtualnyregalbackend.book;

import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.common.model.IntegerRange;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.recommendation.entity.AuthorRecommendation;
import org.hl.wirtualnyregalbackend.recommendation.entity.BookRecommendation;
import org.hl.wirtualnyregalbackend.recommendation.entity.GenreRecommendation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookSpecification {

    private final BookScoresMultiplierProperties multiplierProps;

    public static Predicate getBookQueryPredicate(String query, From<?, Book> book, CriteriaBuilder cb) {
        Expression<String> title = cb.lower(book.get("title"));
        Join<Book, Author> authorJoin = book.join("authors");
        Expression<String> authorFullName = cb.lower(authorJoin.get("fullName"));
        Expression<String> isbn = book.get("isbn");
        String q = query.toLowerCase();
        String pattern = "%" + q + "%";

        return cb.or(
            cb.like(title, pattern),
            cb.like(authorFullName, pattern),
            cb.equal(isbn, q)
        );
    }

    private static Specification<Book> byQuery(String query) {
        return (root, cq, cb) -> getBookQueryPredicate(query, root, cb);
    }

    Specification<Book> sortByRecommendation(User user) {
        return (root, query, cb) -> {
            Subquery<Float> bookScoreSub = query.subquery(Float.class);
            Root<BookRecommendation> br = bookScoreSub.from(BookRecommendation.class);
            bookScoreSub.select(cb.sum(br.get("score")));
            bookScoreSub.where(
                cb.equal(br.get("book"), root),
                cb.equal(br.get("user"), user)
            );

            Subquery<Float> authorScoreSub = query.subquery(Float.class);
            Root<AuthorRecommendation> ar = authorScoreSub.from(AuthorRecommendation.class);
            Join<Book, Author> authors = root.join("authors");
            authorScoreSub.select(cb.sum(ar.get("score")));
            authorScoreSub.where(
                cb.equal(ar.get("author"), authors),
                cb.equal(ar.get("user"), user)
            );

            Subquery<Float> genreScoreSub = query.subquery(Float.class);
            Root<GenreRecommendation> gr = genreScoreSub.from(GenreRecommendation.class);
            Join<Book, Genre> genres = root.join("genres");
            genreScoreSub.select(cb.sum(gr.get("score")));
            genreScoreSub.where(
                cb.equal(gr.get("genre"), genres),
                cb.equal(gr.get("user"), user)
            );


            Expression<Double> bookSelection = cb.prod(cb.coalesce(bookScoreSub.getSelection(), 0.0), multiplierProps.book()).as(Double.class);
            Expression<Double> authorSelection = cb.prod(cb.coalesce(authorScoreSub.getSelection(), 0.0), multiplierProps.author()).as(Double.class);
            Expression<Double> genreSelection = cb.prod(cb.coalesce(genreScoreSub.getSelection(), 0.0), multiplierProps.genre()).as(Double.class);
            Expression<Double> finalScore = cb.sum(
                cb.sum(
                    bookSelection,
                    authorSelection
                ),
                genreSelection
            );

            query.orderBy(cb.desc(finalScore));
            return cb.conjunction();
        };
    }

    Specification<Book> byFilter(BookFilter filter) {
        Specification<Book> spec = Specification.where(null);
        String query = filter.query();
        if (query != null) {
            spec = spec.and(BookSpecification.byQuery(query));
        }

        IntegerRange yearRange = filter.publicationYearRange();
        if (yearRange != null) {
            Integer lte = yearRange.lte();
            if (lte != null) {
                spec = spec.and(ltePublicationYear(lte));
            }

            Integer gte = yearRange.gte();
            if (gte != null) {
                spec = spec.and(gtePublicationYear(gte));
            }
        }

        IntegerRange pageRange = filter.pageCountRange();
        if (pageRange != null) {
            Integer lte = pageRange.lte();
            Integer gte = pageRange.gte();
            if (lte != null) {
                spec = spec.and(ltePageCount(lte));
            }
            if (gte != null) {
                spec = spec.and(gtePageCount(gte));
            }
        }

        Long authorId = filter.authorId();
        if (authorId != null) {
            spec = spec.and(byAuthorId(authorId));
        }

        Long genreId = filter.genreId();
        if (genreId != null) {
            spec = spec.and(byGenreId(genreId));
        }

        Long formatId = filter.formatId();
        if (formatId != null) {
            spec = spec.and(byFormatId(formatId));
        }

        Long publisherId = filter.publisherId();
        if (publisherId != null) {
            spec = spec.and(byPublisherId(publisherId));
        }

        return spec;
    }

    private Specification<Book> ltePublicationYear(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), lte);
    }

    private Specification<Book> gtePublicationYear(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), gte);
    }

    private Specification<Book> ltePageCount(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("pageCount"), lte);
    }

    private Specification<Book> gtePageCount(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("pageCount"), gte);
    }

    private Specification<Book> byAuthorId(Long authorId) {
        return (root, query, cb) -> {
            Join<Book, Author> authors = root.join("authors");
            return cb.equal(authors.get("id"), authorId);
        };
    }

    private Specification<Book> byGenreId(Long genreId) {
        return (root, query, cb) -> {
            Join<Book, Genre> genres = root.join("genres");
            return cb.equal(genres.get("id"), genreId);
        };
    }

    private Specification<Book> byPublisherId(Long publisherId) {
        return (root, query, cb) -> cb.equal(root.get("publisher").get("id"), publisherId);
    }

    private Specification<Book> byFormatId(Long formatId) {
        return (root, query, cb) -> cb.equal(root.get("format").get("id"), formatId);
    }

}
