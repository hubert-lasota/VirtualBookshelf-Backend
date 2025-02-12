package org.hl.wirtualnyregalbackend.publisher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;

import static org.hl.wirtualnyregalbackend.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "publisher")
public class Publisher extends UpdatableBaseEntity {

    private String name;

    protected Publisher() { }

    public Publisher(String name) {
        this.name = baseValidateString(name, "name");
    }


    public String getName() {
        return name;
    }

}
