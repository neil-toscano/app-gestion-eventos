package es.neil.api.repository;

import es.neil.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
    boolean existsById(Long id);
}
