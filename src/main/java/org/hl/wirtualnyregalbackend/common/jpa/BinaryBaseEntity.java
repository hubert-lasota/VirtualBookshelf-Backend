package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BinaryBaseEntity extends BaseEntity {

    @Column(name = "binary_data")
    protected byte[] binaryData;

    @Column(name = "mime_type")
    protected String mimeType;

    @Column(name = "file_name")
    protected String fileName;

    protected BinaryBaseEntity(MultipartFile file) throws IOException {
        this.binaryData = file.getBytes();
        this.mimeType = file.getContentType();
        this.fileName = file.getOriginalFilename();
    }

}
