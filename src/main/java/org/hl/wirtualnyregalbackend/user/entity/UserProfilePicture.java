package org.hl.wirtualnyregalbackend.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "user_profile_picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePicture extends BaseEntity {

    @Column
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_picture_binary_id")
    private UserProfilePictureBinary binaryPicture;

}
