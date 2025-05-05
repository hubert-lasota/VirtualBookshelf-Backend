package org.hl.wirtualnyregalbackend.publisher.dao;

import org.hl.wirtualnyregalbackend.publisher.model.Publisher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PublisherRepository {

    Publisher save(Publisher publisher);

    boolean existsByNameIgnoreCase(String name);

    Optional<Publisher> findById(Long id);

    Set<Publisher> findByIds(List<Long> ids);

}
