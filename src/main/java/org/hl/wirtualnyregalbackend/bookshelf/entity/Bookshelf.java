package org.hl.wirtualnyregalbackend.bookshelf.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "bookshelf")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Bookshelf extends BaseEntity {

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private BookshelfType type;

    @Column
    private String description;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @Setter(AccessLevel.NONE)
//    @Getter(AccessLevel.NONE)
//    @OneToMany(mappedBy = "bookshelf", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<BookshelfBook> books;
//
//    public Bookshelf(String name, BookshelfType type, String description, User user) {
//        this.name = name;
//        this.type = type;
//        this.description = description;
//        this.user = user;
//    }
//
//    public void removeBookshelfBook(Long bookshelfBookId) {
//        this.books.removeIf((book) -> book.getId().equals(bookshelfBookId));
//    }

}
