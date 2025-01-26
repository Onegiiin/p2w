package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotsRepository extends JpaRepository<Lot, Integer> {
    List<Lot> findAllByCategory_Id(Integer categoryId);

    List<Lot> findByNameContainsIgnoreCase(String name);
}
