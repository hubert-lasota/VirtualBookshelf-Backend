package org.hl.wirtualnyregalbackend.publisher;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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
    public List<Publisher> saveAll(List<Publisher> publishers) {
        return publisherRepository.saveAll(publishers);
    }

    @Override
    public List<Publisher> findByNamesIgnoreCase(Collection<String> names) {
        return publisherRepository.findByNamesIgnoreCase(names);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return publisherRepository.existsByNameIgnoreCase(name);
    }

}

@Repository
interface SpringJpaPublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("select p from Publisher p where lower(p.name) in :names")
    List<Publisher> findByNamesIgnoreCase(Collection<String> names);

    boolean existsByNameIgnoreCase(String name);

}

