package org.hl.wirtualnyregalbackend.publisher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Publisher extends BaseEntity {

    private String name;

}
