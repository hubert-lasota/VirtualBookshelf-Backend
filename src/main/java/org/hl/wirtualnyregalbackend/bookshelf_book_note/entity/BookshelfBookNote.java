package org.hl.wirtualnyregalbackend.bookshelf_book_note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "bookshelf_book_note")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookshelfBookNote extends BaseEntity {

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "start_page")
    private Integer startPage;

    @Column(name = "end_page")
    private Integer endPage;

    @ManyToOne
    @JoinColumn(name = "bookshelf_book_id")
    private BookshelfBook bookshelfBook;

}
