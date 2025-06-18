package org.hl.wirtualnyregalbackend.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.security.entity.User;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Challenge extends BaseEntity {

    @Column
    private String description;

    @Embedded
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
