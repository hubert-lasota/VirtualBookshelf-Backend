package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;


@Entity
@Table(name = "author_profile_picture")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthorProfilePicture extends BaseEntity {

    @Column
    private String url;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_profile_picture_binary_id")
    private AuthorProfilePictureBinary authorProfilePictureBinary;

    public AuthorProfilePicture(String url) {
        this.url = url;
    }

}
