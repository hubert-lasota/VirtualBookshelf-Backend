package org.hl.wirtualnyregalbackend.publisher.dao;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface SpringJpaPublisherRepository extends JpaRepository<Publisher, Long> {


    boolean existsByNameIgnoreCase(String name);

}

@Repository
class JpaPublisherRepository implements PublisherRepository {

    private final SpringJpaPublisherRepository publisherRepository;

    public JpaPublisherRepository(SpringJpaPublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }


    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return publisherRepository.existsByNameIgnoreCase(name);
    }

}

