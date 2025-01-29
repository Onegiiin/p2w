package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByConsumer_id(int userId);
}
