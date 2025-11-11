package tech.hiramchavez.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.hiramchavez.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    Category getCategoryByName(String name);

}
