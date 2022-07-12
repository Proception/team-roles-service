package com.tempo.teamroles.service;

import com.tempo.teamroles.entity.Roles;
import com.tempo.teamroles.entity.UserRoles;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Iterable<Roles> getRoles(int page, int size, boolean sortDirection);
    Roles createRole(Roles role);
    void assignRole(UserRoles userRoles);
    Optional<Roles> getRole(Long roleId);
    Optional<Roles> findRoleByName(String roleName);
    Optional<UserRoles> findByTeamIdAndUserId(String teamId, String userId);
    List<UserRoles> findByTeamIdAndRoleId(String teamId, Long userId);
}
