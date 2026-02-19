package es.neil.api.repository;

import es.neil.api.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IEventRepository  extends JpaRepository<Event, Long> {
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
