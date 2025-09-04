package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
