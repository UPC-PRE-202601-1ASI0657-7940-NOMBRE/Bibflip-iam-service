package pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmailIgnoreCase(String email);
}
