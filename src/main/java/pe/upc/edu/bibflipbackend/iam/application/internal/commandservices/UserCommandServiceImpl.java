package pe.upc.edu.bibflipbackend.iam.application.internal.commandservices;

import pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.hashing.HashingService;
import pe.upc.edu.bibflipbackend.iam.application.internal.outboundservices.tokens.TokenService;
import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignInCommand;
import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignUpCommand;
import pe.upc.edu.bibflipbackend.iam.domain.model.valueobjects.Roles;
import pe.upc.edu.bibflipbackend.iam.domain.services.UserCommandService;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.upc.edu.bibflipbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.upc.edu.bibflipbackend.shared.application.exceptions.InvalidValueException;
import pe.upc.edu.bibflipbackend.shared.application.exceptions.ResourceAlreadyException;
import pe.upc.edu.bibflipbackend.shared.application.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new ResourceAlreadyException("User already exists");
        var roles = command.roles();
        if (roles.isEmpty()) {
            var role = roleRepository.findByName(Roles.ROLE_USER);
            roles.add(role.get());
        }
        roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Role " + command.roles() + " does not exist"))).toList();
        var user = new User(command.username(), hashingService.encode(command.password()), roles, command.email());
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user_username = userRepository.findByUsername(command.username());
        if (user_username.isEmpty()) throw new ResourceNotFoundException("User not found");
        if (!hashingService.matches(command.password(), user_username.get().getPassword()))
            throw new InvalidValueException("Invalid password");
        var currentUser = user_username.get();
        var token = tokenService.generateToken(currentUser.getUsername());
        return Optional.of(ImmutablePair.of(currentUser, token));
    }
}
