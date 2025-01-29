package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffersRepository extends JpaRepository<Offer, Integer> {
    boolean existsByLot_IdAndExecutor_Id(Integer lotId, Integer userId);
    List<Offer> findByLot_Id(Integer lotId);
    List<Offer> findByExecutor_Id(Integer userId);
}
