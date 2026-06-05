package pe.upc.edu.bibflipbackend.iam.domain.services;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignInCommand;
import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
}
