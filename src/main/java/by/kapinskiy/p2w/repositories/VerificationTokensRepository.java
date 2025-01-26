package by.kapinskiy.p2w.repositories;

import by.kapinskiy.p2w.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokensRepository extends JpaRepository<VerificationToken, String> {
}
