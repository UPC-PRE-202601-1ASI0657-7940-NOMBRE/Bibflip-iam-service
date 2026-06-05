package pe.upc.edu.bibflipbackend.iam.application.internal.queryservices;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetAllUsersQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetRoleUserByUserIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByUsernameQuery;
import pe.upc.edu.bibflipbackend.iam.domain.services.UserQueryService;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

    @Override
    public Optional<String> handle(GetRoleUserByUserIdQuery query) {
        return userRepository.findById(query.userId())
                .map(user -> user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.joining(",")));
    }
}
