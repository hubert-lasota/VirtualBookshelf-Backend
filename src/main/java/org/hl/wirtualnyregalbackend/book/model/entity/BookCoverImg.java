package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_cover_img")
public class BookCoverImg extends BaseEntity {

    @Column(name = "cover_img")
    private byte[] coverImg;

    @Column(name = "img_type")
    private String imgType;

    @OneToOne(mappedBy = "coverImg")
    private BookCover cover;

}
