package pe.upc.edu.bibflipbackend.iam.domain.model.queries;

import pe.upc.edu.bibflipbackend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
