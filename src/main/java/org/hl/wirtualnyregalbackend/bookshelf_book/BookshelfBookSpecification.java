package org.hl.wirtualnyregalbackend.bookshelf_book;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.data.jpa.domain.Specification;

class BookshelfBookSpecification {

    private BookshelfBookSpecification() {
    }

    public static Specification<BookshelfBook> byUser(User user) {
        return (root, query, cb) ->
            cb.equal(root.get("bookshelf").get("user"), user);
    }

    public static Specification<BookshelfBook> byQuery(String query) {
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
