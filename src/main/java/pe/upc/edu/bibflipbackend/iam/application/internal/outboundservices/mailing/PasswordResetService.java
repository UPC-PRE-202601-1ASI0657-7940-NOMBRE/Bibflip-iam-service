package pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.mailing;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.PasswordResetToken;
import pe.upc.edu.bibflipbackend.iam.infrastructure.mailing.services.MailService;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.PasswordResetTokenRepository;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.upc.edu.bibflipbackend.shared.application.exceptions.ResourceNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepo;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public PasswordResetService(PasswordResetTokenRepository tokenRepo,
                                MailService mailService,
                                PasswordEncoder passwordEncoder,
                                UserRepository userRepository) {
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void requestReset(String email) {
        var userOpt = userRepository.findByEmailIgnoreCase(email);
        if (userOpt.isEmpty()) throw new ResourceNotFoundException("Usuario no encontrado para el correo: " + email);

        // Eliminar tokens existentes para este email antes de crear uno nuevo
        tokenRepo.deleteByEmail(email);
        tokenRepo.flush(); // Forzar la ejecución inmediata del DELETE

        String code = generate6DigitCode();
        var expiresAt = Instant.now().plus(15, ChronoUnit.MINUTES);
        var token = new PasswordResetToken(email, code, expiresAt);
        tokenRepo.save(token);
        mailService.sendPasswordResetCode(email, code);

        // Limpieza de tokens que expiraron
        tokenRepo.deleteAllByExpiresAtBefore(Instant.now());
    }

    @Transactional
    public void resetPassword(String email, String code, String newPassword) {
        var tokenOpt = tokenRepo.findByEmailAndTokenAndUsedFalse(email, code);
        var token = tokenOpt.orElseThrow(() -> new IllegalArgumentException("Código inválido o ya usado"));
        if (token.getExpiresAt().isBefore(Instant.now())) throw new IllegalArgumentException("Código expirado");

        var user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepo.save(token);
    }

    private String generate6DigitCode() {
        var rnd = new Random();
        return String.format("%06d", rnd.nextInt(1_000_000));
    }
}