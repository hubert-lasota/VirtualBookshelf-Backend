package org.hl.wirtualnyregalbackend.bookshelf.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "bookshelf")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "total_books")
    @Setter(AccessLevel.NONE)
    private Long totalBooks;

    public Bookshelf(String name, BookshelfType type, String description, User user) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.user = user;
        this.totalBooks = 0L;
    }


    public void incrementTotalBooks() {
        this.totalBooks++;
    }

    public void decrementTotalBooks() {
        this.totalBooks--;
    }

}
