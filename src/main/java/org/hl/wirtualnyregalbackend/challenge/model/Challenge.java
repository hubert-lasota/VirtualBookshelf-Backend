package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.RangeDate;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.time.Instant;

import static org.hl.wirtualnyregalbackend.common.ValidationUtils.baseValidateString;

@Entity
public class Challenge extends UpdatableBaseEntity {

    @Column(name = "description")
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startAt", column = @Column(name = "start_at_timestamp")),
            @AttributeOverride(name = "endAt", column = @Column(name = "finish_at_timestamp"))
    })
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    protected Challenge() { }

    public Challenge(String description, Instant startAt, Instant finishAt, User user) {
        this.description = baseValidateString(description, "description");
        this.rangeDate = new RangeDate(startAt, finishAt);
        this.user = user;
    }

    public void updateDescription(String description) {
        this.description = baseValidateString(description, "description");
    }

    public void updateStartAt(Instant startAt) {
        Instant finishAt = rangeDate.getEndAt();
        this.rangeDate = new RangeDate(startAt, finishAt);
    }

    public void updateFinishAt(Instant finishAt) {
        Instant startAt = rangeDate.getStartAt();
        this.rangeDate = new RangeDate(startAt, finishAt);
    }

    public String getDescription() {
        return description;
    }


    public Instant getStartAt() {
        return rangeDate.getStartAt();
    }

    public Instant getFinishAt() {
        return rangeDate.getEndAt();
    }

    public User getUser() {
        return user;
    }

}
