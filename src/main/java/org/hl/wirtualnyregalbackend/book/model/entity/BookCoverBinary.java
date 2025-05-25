package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "book_cover_binary")
public class BookCoverBinary extends BinaryBaseEntity {

    protected BookCoverBinary() {
    }

    public BookCoverBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
