package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
     Optional<User> findByUsername(String name);

     Optional<User> findByEmail(String name);
}
