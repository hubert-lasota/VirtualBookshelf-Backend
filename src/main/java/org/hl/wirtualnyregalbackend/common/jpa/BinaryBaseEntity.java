package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BinaryBaseEntity extends BaseEntity {

    @Column(name = "binary_data")
    protected byte[] binaryData;

    @Column(name = "mime_type")
    protected String mimeType;

    @Column(name = "file_name")
    protected String fileName;

    protected BinaryBaseEntity() {
    }

    protected BinaryBaseEntity(byte[] binaryData, String mimeType, String fileName) {
        this.binaryData = binaryData;
        this.mimeType = mimeType;
        this.fileName = fileName;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }

}
