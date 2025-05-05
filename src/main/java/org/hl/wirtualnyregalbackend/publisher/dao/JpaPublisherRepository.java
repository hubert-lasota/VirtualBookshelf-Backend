package org.hl.wirtualnyregalbackend.publisher.dao;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Set<Publisher> findByIds(List<Long> ids) {
        return publisherRepository.findByIds(ids);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return publisherRepository.existsByNameIgnoreCase(name);
    }

}

@Repository
interface SpringJpaPublisherRepository extends JpaRepository<Publisher, Long> {

    Set<Publisher> findByIds(List<Long> ids);

    boolean existsByNameIgnoreCase(String name);

}

