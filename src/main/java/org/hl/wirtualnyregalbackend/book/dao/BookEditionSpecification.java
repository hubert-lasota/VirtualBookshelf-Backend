package org.hl.wirtualnyregalbackend.book.dao;

import jakarta.persistence.criteria.Join;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.springframework.data.jpa.domain.Specification;

class BookEditionSpecification {

    private BookEditionSpecification() {
    }

    public static Specification<BookEdition> titleIgnoreCaseLike(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<BookEdition> authorFullNameIgnoreCaseLike(String authorFullName) {
        return (root, query, cb) -> {
            Join<Book, BookEdition> bookJoin = root.join("book");
            Join<Author, Book> authorJoin = bookJoin.join("author");
            return cb.like(cb.lower(authorJoin.get("fullName")), "%" + authorFullName + "%");
        };
    }

    public static Specification<BookEdition> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("book").get("isbn"), isbn);
    }

}
