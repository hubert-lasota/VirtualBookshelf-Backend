package org.hl.wirtualnyregalbackend.author;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.data.jpa.domain.Specification;

class AuthorSpecification {

    private AuthorSpecification() {
    }

    public static Specification<Author> availableInBookshelf(boolean availableInBookshelf, User user) {
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

}
