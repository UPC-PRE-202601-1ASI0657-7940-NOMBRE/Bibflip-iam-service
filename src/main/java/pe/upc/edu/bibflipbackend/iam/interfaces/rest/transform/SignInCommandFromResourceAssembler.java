package pe.upc.edu.bibflipbackend.iam.interfaces.rest.transform;

import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignInCommand;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}
