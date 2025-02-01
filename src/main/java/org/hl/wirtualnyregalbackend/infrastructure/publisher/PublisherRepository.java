package org.hl.wirtualnyregalbackend.infrastructure.publisher;

import org.hl.wirtualnyregalbackend.application.publisher.Publisher;

import java.util.Collection;
import java.util.List;

public interface PublisherRepository {

    Publisher save(Publisher publisher);

    List<Publisher> saveAll(List<Publisher> publishers);

    boolean existsByNameIgnoreCase(String name);

    List<Publisher> findByNamesIgnoreCase(Collection<String> names);

}
