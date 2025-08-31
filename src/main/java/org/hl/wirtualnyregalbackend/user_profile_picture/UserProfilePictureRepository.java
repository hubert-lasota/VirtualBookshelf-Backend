package org.hl.wirtualnyregalbackend.user_profile_picture;

import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {
}
