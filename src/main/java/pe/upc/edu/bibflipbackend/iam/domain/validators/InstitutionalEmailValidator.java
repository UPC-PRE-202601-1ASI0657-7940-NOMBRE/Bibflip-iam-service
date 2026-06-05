package pe.upc.edu.bibflipbackend.iam.domain.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.SignUpResource;

public class InstitutionalEmailValidator implements ConstraintValidator<InstitutionalEmailRequired, SignUpResource> {

    @Override
    public boolean isValid(SignUpResource resource, ConstraintValidatorContext context) {
        if (resource == null) return true;

        String role = resource.roles() != null && !resource.roles().isEmpty() ? resource.roles().get(0) : null;
        String email = resource.email() != null ? resource.email().toLowerCase() : null;
        System.out.println("Email: " + email + " | Role: " + role);

        // Solo valida correos si no son admin ni supervisor
        if (role == null || email == null) return true;

        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_SUPERVISOR")) {
            return true; // se permite cualquier email
        }

        // Validar formato institucional
        return email.endsWith("@upc.edu.pe");
    }
}
