package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
