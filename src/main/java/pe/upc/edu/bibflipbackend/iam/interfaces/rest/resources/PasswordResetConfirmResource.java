package pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmResource(
        @NotBlank(message = "El email es requerido")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "El código es requerido")
        @Size(min = 6, max = 6, message = "El código debe tener 6 dígitos")
        String code,

        @NotBlank(message = "La nueva contraseña es requerida")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String newPassword
) {}