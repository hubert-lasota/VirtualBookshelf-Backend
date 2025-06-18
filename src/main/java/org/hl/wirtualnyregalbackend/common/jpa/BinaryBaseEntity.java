package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BinaryBaseEntity extends BaseEntity {

    @Column(name = "binary_data")
    protected byte[] binaryData;

    @Column(name = "mime_type")
    protected String mimeType;

    @Column(name = "file_name")
    protected String fileName;

}
