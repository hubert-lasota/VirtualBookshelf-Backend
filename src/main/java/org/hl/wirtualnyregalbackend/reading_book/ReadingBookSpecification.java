package org.hl.wirtualnyregalbackend.reading_book;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.data.jpa.domain.Specification;

class ReadingBookSpecification {

    private ReadingBookSpecification() {
    }

    public static Specification<ReadingBook> byUser(User user) {
        return (root, query, cb) ->
            cb.equal(root.get("bookshelf").get("user"), user);
    }

    public static Specification<ReadingBook> byQuery(String query) {
        return (root, criteriaQuery, cb) -> {
            Path<Book> book = root.get("book");
            Expression<String> title = cb.lower(book.get("title"));
            Expression<String> authorFullName = cb.lower(book.get("authors").get("fullName"));
            Expression<String> isbn = cb.lower(book.get("isbn"));
            String pattern = "%" + query.toLowerCase() + "%";
            return cb.or(
                cb.like(title, pattern),
                cb.like(authorFullName, pattern),
                cb.equal(isbn, query.toLowerCase())
            );
        };
    }

}
