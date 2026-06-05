package pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.PasswordResetToken;

import java.time.Instant;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByEmailAndTokenAndUsedFalse(String email, String token);

    @Modifying
    @Transactional
    void deleteAllByExpiresAtBefore(Instant time);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetToken p WHERE p.email = :email")
    void deleteByEmail(String email);
}
