package pe.upc.edu.bibflipbackend.iam.application.internal.queryservices;

import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetAllRolesQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetRoleByNameQuery;
import pe.upc.edu.bibflipbackend.iam.domain.services.RoleQueryService;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(query.name());
    }
}
