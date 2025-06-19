package org.hl.wirtualnyregalbackend.bookshelf.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "bookshelf_book_note")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookshelfBookNote extends BaseEntity {

    @Column
    private String content;

    @Column(name = "start_page")
    private Integer startPage;

    @Column(name = "end_page")
    private Integer endPage;

    @ManyToOne
    @JoinColumn(name = "bookshelf_book_id")
    private BookshelfBook bookshelfBook;

    public BookshelfBookNote(String content,
                             Integer startPage,
                             Integer endPage) {
        this.content = content;
        this.startPage = startPage;
        this.endPage = endPage;
    }


    void setBookshelfBook(BookshelfBook bookshelfBook) {
        this.bookshelfBook = bookshelfBook;
    }

}
