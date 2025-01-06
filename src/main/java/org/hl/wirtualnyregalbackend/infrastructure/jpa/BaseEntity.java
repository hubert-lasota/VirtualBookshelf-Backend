package org.hl.wirtualnyregalbackend.infrastructure.jpa;

import jakarta.persistence.*;

import java.time.Clock;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at_timestamp", nullable = false)
    protected Instant createdAt;

    protected BaseEntity() { }


    @PrePersist
    protected void prePersist() {
        this.createdAt = Instant.now(Clock.systemUTC());
    }

    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}