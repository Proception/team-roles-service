package com.tempo.teamroles.repository;

import com.tempo.teamroles.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    Optional<UserRoles> findByTeamIdAndUserId(String teamId, String userId);
    List<UserRoles> findByTeamIdAndRoleId(String teamId, Long roleId);
}
