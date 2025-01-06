package org.hl.wirtualnyregalbackend.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;

import java.time.Clock;
import java.time.Instant;

@MappedSuperclass
public abstract class UpdatableBaseEntity extends BaseEntity {

    @Column(name = "updated_at_timestamp")
    protected Instant updatedAt;

    protected UpdatableBaseEntity() { }


    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = Instant.now(Clock.systemUTC());
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
