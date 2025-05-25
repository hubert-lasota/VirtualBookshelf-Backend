package org.hl.wirtualnyregalbackend.book;

import jakarta.persistence.criteria.Join;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.springframework.data.jpa.domain.Specification;

class BookSpecification {

    private BookSpecification() {
    }

    public static Specification<Book> titleIgnoreCaseLike(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> authorFullNameIgnoreCaseLike(String authorFullName) {
        return (root, query, cb) -> {
            Join<Author, Book> authorJoin = root.join("authors");
            return cb.like(cb.lower(authorJoin.get("fullName")), "%" + authorFullName + "%");
        };
    }

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("book").get("isbn"), isbn);
    }

}
