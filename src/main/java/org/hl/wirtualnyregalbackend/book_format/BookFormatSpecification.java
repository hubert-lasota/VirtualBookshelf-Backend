package org.hl.wirtualnyregalbackend.book_format;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.domain.Specification;

class BookFormatSpecification {

    private BookFormatSpecification() {
    }

    public static Specification<BookFormat> availableInBookshelf(boolean availableInBookshelf, User user) {
        return ((root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ReadingBook> rb = subquery.from(ReadingBook.class);
            Join<ReadingBook, Book> bookJoin = rb.join("book");
            Join<ReadingBook, Bookshelf> bookshelfJoin = rb.join("bookshelf");

            subquery
                .select(bookJoin.get("format").get("id"))
                .where(cb.equal(bookshelfJoin.get("user"), user))
                .distinct(true);

            Predicate predicate = root.get("id").in(subquery);
            return availableInBookshelf ? predicate : cb.not(predicate);
        });
    }

}
