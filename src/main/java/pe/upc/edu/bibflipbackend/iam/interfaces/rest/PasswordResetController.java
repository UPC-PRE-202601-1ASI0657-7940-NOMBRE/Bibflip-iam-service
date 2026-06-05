package pe.upc.edu.bibflipbackend.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.mailing.PasswordResetService;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.PasswordResetConfirmResource;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.PasswordResetRequestResource;

@RestController
@RequestMapping(value="/api/v1/password-recovery", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Password Recovery", description = "Password Recovery Endpoints")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request")
    public ResponseEntity<MessageResponse> requestPasswordReset(@RequestBody PasswordResetRequestResource resource) {
        try {
            passwordResetService.requestReset(resource.email());
            return ResponseEntity.ok(new MessageResponse("Código de recuperación enviado al correo electrónico"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al procesar la solicitud: " + e.getMessage()));
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<MessageResponse> confirmPasswordReset(@RequestBody PasswordResetConfirmResource resource) {
        try {
            passwordResetService.resetPassword(resource.email(), resource.code(), resource.newPassword());
            return ResponseEntity.ok(new MessageResponse("Contraseña actualizada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error al restablecer la contraseña"));
        }
    }

    // Inner class for message responses
    public record MessageResponse(String message) {}
}