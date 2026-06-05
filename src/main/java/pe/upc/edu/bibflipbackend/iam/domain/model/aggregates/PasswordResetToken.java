package pe.upc.edu.bibflipbackend.iam.domain.model.aggregates;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pe.upc.edu.bibflipbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.Instant;

@Getter
@Entity
public class PasswordResetToken extends AuditableAbstractAggregateRoot<PasswordResetToken> {
    @Column(unique = true)
    private String email;

    @Column
    private String token;

    @Column
    private Instant expiresAt;

    // ← Cambiar esto
    @Setter
    @Column
    private boolean used;

    protected PasswordResetToken() { /* JPA */ }

    public PasswordResetToken(String email, String token, Instant expiresAt) {
        this.email = email;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = false;
    }

}
