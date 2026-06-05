package pe.upc.edu.bibflipbackend.iam.application.internal.commandservices;

import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SeedRolesCommand;
import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.domain.model.valueobjects.Roles;
import pe.upc.edu.bibflipbackend.iam.domain.services.RoleCommandService;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
