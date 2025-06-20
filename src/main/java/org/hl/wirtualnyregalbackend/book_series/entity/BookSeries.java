package org.hl.wirtualnyregalbackend.book_series.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class BookSeries extends BaseEntity {

    @Column
    private String name;

}
