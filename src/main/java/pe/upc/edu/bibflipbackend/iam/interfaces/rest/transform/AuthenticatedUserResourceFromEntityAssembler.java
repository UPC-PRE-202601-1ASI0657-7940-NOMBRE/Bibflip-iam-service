package pe.upc.edu.bibflipbackend.iam.interfaces.rest.transform;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String token) {
        var roles = entity.getRoles().stream().map(Role::getStringName).toList();
        return new AuthenticatedUserResource(entity.getId(), entity.getUsername(), token, roles, entity.getEmail());
    }
}
