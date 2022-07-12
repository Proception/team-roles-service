package com.tempo.teamroles.service;

import com.tempo.teamroles.entity.Roles;
import com.tempo.teamroles.entity.UserRoles;
import com.tempo.teamroles.repository.RolesRepository;
import com.tempo.teamroles.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RoleService {
    private RolesRepository rolesRepository;
    private UserRolesRepository userRolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository, UserRolesRepository userRolesRepository) {
        this.rolesRepository = rolesRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public Iterable<Roles> getRoles(int page, int size, boolean sortDirection) {
        return rolesRepository
                .findAll(PageRequest.of(page, size, sortDirection ? Sort.by("createdDate").ascending() : Sort.by("createdDate").descending()));
    }

    @Override
    public Roles createRole(Roles role) {
        return rolesRepository.save(role);
    }

    @Override
    public void assignRole(UserRoles userRoles) {
        Optional<UserRoles> doesUserHaveRoleInTeam = findByTeamIdAndUserId(userRoles.getTeamId(), userRoles.getUserId());
        if(!doesUserHaveRoleInTeam.isPresent()){
            userRolesRepository.save(userRoles);
            return;
        }
        throw new DataIntegrityViolationException("User with ID:" + userRoles.getUserId() + " already has a role in Team: "+ userRoles.getTeamId());
    }

    @Override
    public Optional<Roles> getRole(Long roleId) {
        return rolesRepository.findById(roleId);
    }

    @Override
    public Optional<Roles> findRoleByName(String roleName) {
        return rolesRepository.findRolesByName(roleName);
    }

    @Override
    public Optional<UserRoles> findByTeamIdAndUserId(String teamId, String userId) {
        Optional<UserRoles> userRoles = userRolesRepository.findByTeamIdAndUserId(teamId, userId);

        return userRoles;
    }

    @Override
    public List<UserRoles> findByTeamIdAndRoleId(String teamId, Long roleId) {
        return userRolesRepository.findByTeamIdAndRoleId(teamId, roleId);
    }
}
