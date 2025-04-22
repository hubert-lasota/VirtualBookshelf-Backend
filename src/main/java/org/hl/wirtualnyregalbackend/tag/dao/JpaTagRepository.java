package org.hl.wirtualnyregalbackend.tag.dao;

import org.hl.wirtualnyregalbackend.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaTagRepository {

    private final SpringJpaTagRepository tagRepository;

    public JpaTagRepository(SpringJpaTagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


}


@Repository
interface SpringJpaTagRepository extends JpaRepository<Tag, Long> {

}