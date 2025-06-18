package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.Instant;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;


    @PrePersist
    protected void prePersist() {
        this.createdAt = Instant.now(Clock.systemUTC());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = Instant.now(Clock.systemUTC());
    }


}
