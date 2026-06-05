package pe.upc.edu.bibflipbackend.iam.domain.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InstitutionalEmailValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InstitutionalEmailRequired {
    String message() default "Solo los usuarios normales deben usar correo @upc.edu.pe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
