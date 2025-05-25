package org.hl.wirtualnyregalbackend.publisher.dao;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;

import java.util.Optional;

public interface PublisherRepository {

    Publisher save(Publisher publisher);

    boolean existsByNameIgnoreCase(String name);

    Optional<Publisher> findById(Long id);

}
