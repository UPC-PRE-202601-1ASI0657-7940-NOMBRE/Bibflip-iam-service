package pe.upc.edu.bibflipbackend.iam.domain.model.aggregates;

import jakarta.validation.constraints.Email;
import lombok.Setter;
import pe.upc.edu.bibflipbackend.iam.domain.model.entities.Role;
import pe.upc.edu.bibflipbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @Getter
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 120)
    private String password;

    @Email
    @Getter
    @Size(max = 360)
    @Column(unique = true)
    private String email;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private final Set<Role> roles;

    public User() { this.roles = new HashSet<>();}

    public User(String username, String password, String email) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, List<Role> roles, String email) {
        this(username, password, email);
        addRoles(roles);
    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public void addRoles(List<Role> roles) {
        var validatedRoles = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoles);
    }
}
