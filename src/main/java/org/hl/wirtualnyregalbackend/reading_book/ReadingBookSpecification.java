package org.hl.wirtualnyregalbackend.reading_book;

import jakarta.persistence.criteria.Join;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.BookSpecification;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.common.model.IntegerRangeFilter;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.domain.Specification;

class ReadingBookSpecification {

    private ReadingBookSpecification() {
    }


    public static Specification<ReadingBook> byFilter(BookFilter filter) {
        Specification<ReadingBook> spec = Specification.where(null);
        String query = filter.query();
        if (query != null) {
            spec = spec.and(ReadingBookSpecification.byQuery(query));
        }

        IntegerRangeFilter yearRange = filter.publicationYearRange();
        if (yearRange != null) {
            Integer lte = yearRange.lte();
            Integer gte = yearRange.gte();
            if (lte != null) {
                spec = spec.and(ReadingBookSpecification.ltePublicationYear(lte));
            }
            if (gte != null) {
                spec = spec.and(ReadingBookSpecification.gtePublicationYear(gte));
            }
        }

        IntegerRangeFilter pageRange = filter.pageCountRange();
        if (pageRange != null) {
            Integer lte = pageRange.lte();
            if (lte != null) {
                spec = spec.and(ReadingBookSpecification.ltePageCount(lte));
            }

            Integer gte = pageRange.gte();
            if (gte != null) {
                spec = spec.and(ReadingBookSpecification.gtePageCount(gte));
            }
        }

        Long authorId = filter.authorId();
        if (authorId != null) {
            spec = spec.and(ReadingBookSpecification.byAuthorId(authorId));
        }

        Long genreId = filter.genreId();
        if (genreId != null) {
            spec = spec.and(ReadingBookSpecification.byGenreId(genreId));
        }

        Long formatId = filter.formatId();
        if (formatId != null) {
            spec = spec.and(ReadingBookSpecification.byFormatId(formatId));
        }

        Long publisherId = filter.publisherId();
        if (publisherId != null) {
            spec = spec.and(ReadingBookSpecification.byPublisherId(publisherId));
        }

        return spec;
    }

    public static Specification<ReadingBook> byUser(User user) {
        return (root, query, cb) ->
            cb.equal(root.get("bookshelf").get("user"), user);
    }

    private static Specification<ReadingBook> byQuery(String query) {
        return (root, cq, cb) -> {
            Join<ReadingBook, Book> book = root.join("book");
            return BookSpecification.getBookQueryPredicate(query, book, cb);
        };
    }

    private static Specification<ReadingBook> ltePublicationYear(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("book").get("publicationYear"), lte);
    }

    private static Specification<ReadingBook> gtePublicationYear(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("book").get("publicationYear"), gte);
    }

    private static Specification<ReadingBook> ltePageCount(Integer lte) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("book").get("pageCount"), lte);
    }

    private static Specification<ReadingBook> gtePageCount(Integer gte) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("book").get("pageCount"), gte);
    }

    private static Specification<ReadingBook> byAuthorId(Long authorId) {
        return (root, query, cb) -> {
            Join<ReadingBook, Book> book = root.join("book");
            Join<Book, Author> authors = book.join("authors");
            return cb.equal(authors.get("id"), authorId);
        };
    }

    private static Specification<ReadingBook> byGenreId(Long genreId) {
        return (root, query, cb) -> {
            Join<ReadingBook, Book> book = root.join("book");
            Join<Book, Genre> genres = book.join("genres");
            return cb.equal(genres.get("id"), genreId);
        };
    }

    private static Specification<ReadingBook> byFormatId(Long formatId) {
        return (root, query, cb) -> cb.equal(root.get("book").get("format").get("id"), formatId);
    }

    private static Specification<ReadingBook> byPublisherId(Long publisherId) {
        return (root, query, cb) -> cb.equal(root.get("book").get("publisher").get("id"), publisherId);
    }

}
