package pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources;

import pe.upc.edu.bibflipbackend.iam.domain.validators.InstitutionalEmailRequired;
import java.util.List;

@InstitutionalEmailRequired
public record SignUpResource(String username, String password, List<String> roles, String email) { }