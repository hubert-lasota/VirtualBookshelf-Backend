package org.hl.wirtualnyregalbackend.challenge.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.security.entity.User;


@Entity
public class Challenge extends BaseEntity {

    @Column
    private String description;

    @Embedded
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    protected Challenge() {
    }

    public Challenge(String description, RangeDate rangeDate, User user) {
        this.description = description;
        this.rangeDate = rangeDate;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RangeDate getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(RangeDate rangeDate) {
        this.rangeDate = rangeDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
