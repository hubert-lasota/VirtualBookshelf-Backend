package org.hl.wirtualnyregalbackend.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "user_profile_picture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserProfilePicture extends BaseEntity {

    @Column
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_picture_binary_id")
    @ToString.Exclude
    private UserProfilePictureBinary binaryPicture;

}
