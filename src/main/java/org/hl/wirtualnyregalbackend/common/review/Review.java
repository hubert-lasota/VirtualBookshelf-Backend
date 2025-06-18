package org.hl.wirtualnyregalbackend.common.review;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Review extends BaseEntity {

    @Column
    private Float rating;

    @Column
    private String content;

}
