package org.hl.wirtualnyregalbackend.book_cover.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_cover")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class BookCover extends BaseEntity {

    @Column
    private String url;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    @OneToOne
    @JoinColumn(name = "book_cover_binary_id")
    @ToString.Exclude
    private BookCoverBinary coverBinary;


    public BookCover(String url, BookCoverBinary coverBinary) {
        this.url = url;
        this.coverBinary = coverBinary;
    }

}
