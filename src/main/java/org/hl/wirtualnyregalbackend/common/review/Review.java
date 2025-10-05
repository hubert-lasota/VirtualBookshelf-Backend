package org.hl.wirtualnyregalbackend.common.review;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class Review extends BaseEntity {

    @Column
    protected float rating;

    @Column
    protected String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @Setter(AccessLevel.NONE)
    protected User user;

}
