package pe.upc.edu.bibflipbackend.iam.interfaces.rest.transform;

import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(entity.getId(), entity.getStringName());

    }
}
