package org.hl.wirtualnyregalbackend.book;

import jakarta.persistence.criteria.*;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.common.model.NumberRangeFilter;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification() {
    }

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

    static Specification<Book> byFilter(BookFilter filter) {
        Specification<Book> spec = Specification.where(null);
        String query = filter.query();
        if (query != null) {
            spec = spec.and(BookSpecification.byQuery(query));
        }

        NumberRangeFilter yearRange = filter.publicationYearRange();
        if (yearRange != null) {
            Integer lte = yearRange.lte();
            if (lte != null) {
                spec = spec.and(BookSpecification.ltePublicationYear(lte));
            }

            Integer gte = yearRange.gte();
            if (gte != null) {
                spec = spec.and(BookSpecification.gtePublicationYear(gte));
            }
        }

        NumberRangeFilter pageRange = filter.pageCountRange();
        if (pageRange != null) {
            Integer lte = pageRange.lte();
            Integer gte = pageRange.gte();
            if (lte != null) {
                spec = spec.and(BookSpecification.ltePageCount(lte));
            }
            if (gte != null) {
                spec = spec.and(BookSpecification.gtePageCount(gte));
            }
        }

        Long authorId = filter.authorId();
        if (authorId != null) {
            spec = spec.and(BookSpecification.byAuthorId(authorId));
        }

        Long genreId = filter.genreId();
        if (genreId != null) {
            spec = spec.and(BookSpecification.byGenreId(genreId));
        }

        Long formatId = filter.formatId();
        if (formatId != null) {
            spec = spec.and(BookSpecification.byFormatId(formatId));
        }

        Long publisherId = filter.publisherId();
        if (publisherId != null) {
            spec = spec.and(BookSpecification.byPublisherId(publisherId));
        }

        return spec;
    }

    private static Specification<Book> byQuery(String query) {
        return (root, cq, cb) -> getBookQueryPredicate(query, root, cb);
    }

    private static Specification<Book> ltePublicationYear(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), lte);
    }

    private static Specification<Book> gtePublicationYear(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), gte);
    }

    private static Specification<Book> ltePageCount(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("pageCount"), lte);
    }

    private static Specification<Book> gtePageCount(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("pageCount"), gte);
    }

    private static Specification<Book> byAuthorId(Long authorId) {
        return (root, query, cb) -> {
            Join<Book, Author> authors = root.join("authors");
            return cb.equal(authors.get("id"), authorId);
        };
    }

    private static Specification<Book> byGenreId(Long genreId) {
        return (root, query, cb) -> {
            Join<Book, Genre> genres = root.join("genres");
            return cb.equal(genres.get("id"), genreId);
        };
    }

    private static Specification<Book> byPublisherId(Long publisherId) {
        return (root, query, cb) -> cb.equal(root.get("publisher").get("id"), publisherId);
    }

    private static Specification<Book> byFormatId(Long formatId) {
        return (root, query, cb) -> cb.equal(root.get("format").get("id"), formatId);
    }

}
