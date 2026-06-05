package pe.upc.edu.bibflipbackend.iam.domain.services;

import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
