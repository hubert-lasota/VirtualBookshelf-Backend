package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.auth.entity.User;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Author extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_profile_picture_id")
    private AuthorProfilePicture AuthorProfilePicture;
    // TODO do usuniecia user z autora
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
