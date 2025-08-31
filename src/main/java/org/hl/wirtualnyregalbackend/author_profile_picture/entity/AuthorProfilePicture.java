package org.hl.wirtualnyregalbackend.author_profile_picture.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;


@Entity
@Table(name = "author_profile_picture")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthorProfilePicture extends BaseEntity {

    @Column
    @Setter
    private String url;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_profile_picture_binary_id")
    private AuthorProfilePictureBinary authorProfilePictureBinary;

    public AuthorProfilePicture(String url) {
        this.url = url;
    }

}
