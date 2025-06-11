package org.hl.wirtualnyregalbackend.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;


@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column
    private String name;

    protected Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }


    public void setNameIfNotNull(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

}
