package org.hl.wirtualnyregalbackend.book_cover.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BinaryBaseEntity;

@Entity
@Table(name = "book_cover_binary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCoverBinary extends BinaryBaseEntity {

    public BookCoverBinary(byte[] binaryData, String mimeType, String fileName) {
        super(binaryData, mimeType, fileName);
    }

}
