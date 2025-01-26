package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
     List<Category> findByNameContainsIgnoreCase(String name);

     List<Category> findByParentCategoryIsNull();

     Optional<Category> findByNameAndParentCategoryIsNull(String name);
}
