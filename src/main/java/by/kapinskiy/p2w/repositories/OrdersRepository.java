package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
