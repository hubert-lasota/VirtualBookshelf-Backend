package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.*;

import java.time.Clock;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    protected BaseEntity() {
    }


    @PrePersist
    protected void prePersist() {
        this.createdAt = Instant.now(Clock.systemUTC());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = Instant.now(Clock.systemUTC());
    }

    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
