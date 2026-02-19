package es.neil.api.repository;

import es.neil.api.domain.Category;
import es.neil.api.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISpeakerRepository extends JpaRepository<Speaker, Long> {
    Optional<Speaker> findByEmail(String email);
    Boolean existsByEmail(String email);
}
