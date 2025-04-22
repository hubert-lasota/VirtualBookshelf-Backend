package org.hl.wirtualnyregalbackend.publisher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "publisher")
public class Publisher extends BaseEntity {

    private String name;

    protected Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

}
