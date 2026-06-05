package pe.upc.edu.bibflipbackend.iam.interfaces.rest.transform;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        var roles = entity.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(entity.getId(), entity.getUsername(), roles, entity.getEmail());
    }
}
