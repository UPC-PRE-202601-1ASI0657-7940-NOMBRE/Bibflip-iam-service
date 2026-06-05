package pe.upc.edu.bibflipbackend.iam.interfaces.acl;

import pe.upc.edu.bibflipbackend.iam.domain.model.commands.SignUpCommand;
import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetRoleUserByUserIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByIdQuery;
import pe.upc.edu.bibflipbackend.iam.domain.model.queries.GetUserByUsernameQuery;
import pe.upc.edu.bibflipbackend.iam.domain.services.UserCommandService;
import pe.upc.edu.bibflipbackend.iam.domain.services.UserQueryService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
@Service
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Creates a user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email the email of the user.
     * @return The id of the created user.
     */
    public Long createUser(String username, String password, String email) {
        var signUpCommand = new SignUpCommand(username, password, List.of(Role.getDefaultRole()), email);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Creates a user with the given username, password and roles.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param roleNames The names of the roles of the user. When a role does not exist, it is ignored.
     * @return The id of the created user.
     */
    public Long createUser(String username, String password, List<String> roleNames, String email) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(username, password, roles, email);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Fetches the id of the user with the given username.
     * @param username The username of the user.
     * @return The id of the user.
     */
    public Long fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    /**
     * Fetches the username of the user with the given id.
     * @param userId The id of the user.
     * @return The username of the user.
     */
    public String fetchUsernameByUserId(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getUsername();
    }

    public boolean existsUser(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        return result.isPresent();
    }

    /**
     * Obtiene los roles de un usuario por su ID.
     * @param userId El ID del usuario.
     * @return Una cadena con los roles del usuario separados por coma, o cadena vacía si el usuario no existe.
     */
    public String getUserRolesByUserId(Long userId) {
        var query = new GetRoleUserByUserIdQuery(userId);
        var result = userQueryService.handle(query);
        return result.orElse(Strings.EMPTY);
    }
}