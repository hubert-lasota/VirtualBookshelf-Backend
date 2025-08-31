package org.hl.wirtualnyregalbackend.author_profile_picture;

import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AuthorProfilePictureRepository extends JpaRepository<AuthorProfilePicture, Long> {
}
