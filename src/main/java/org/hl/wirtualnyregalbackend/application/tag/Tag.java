package org.hl.wirtualnyregalbackend.application.tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;

import static org.hl.wirtualnyregalbackend.application.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "tag")
public class Tag extends UpdatableBaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    protected Tag() { }

    public Tag(String name) {
        this.name = baseValidateString(name, "name");
    }

    public void updateName(String name) {
        this.name = baseValidateString(name, "name");
    }

    public String getName() {
        return name;
    }

}
