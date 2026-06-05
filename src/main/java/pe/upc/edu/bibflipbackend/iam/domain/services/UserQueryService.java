package pe.upc.edu.bibflipbackend.iam.domain.services;

import pe.upc.edu.bibflipbackend.iam.domain.model.aggregates.User;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetAllUsersQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetRoleUserByUserIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<String> handle(GetRoleUserByUserIdQuery query);
}
