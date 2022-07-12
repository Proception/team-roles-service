package com.tempo.teamroles.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tempo.teamroles.controller.v1.request.AssignRole;
import com.tempo.teamroles.controller.v1.request.CreateRole;
import com.tempo.teamroles.dto.response.Response;
import com.tempo.teamroles.dto.response.RolesApiResponse;
import com.tempo.teamroles.entity.Roles;
import com.tempo.teamroles.entity.UserRoles;
import com.tempo.teamroles.service.RoleService;
import com.tempo.teamroles.service.TeamService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Role Controller", description = "This controller is responsible for handling all role specific endpoints")
public class RoleController {
    @Value("${roles.default}")
    private String DEFAULT_ROLE;

    private RoleService roleService;
    private TeamService teamService;

    @Autowired
    public RoleController(RoleService roleService, TeamService teamService) {
        this.roleService = roleService;
        this.teamService = teamService;
    }

    @GetMapping
    public Response getAllRoles(@Parameter(description = "Page is the current page", example = "0") @RequestParam int page,
                                @Parameter(description = "Size is the number of records per page", example = "10") @RequestParam int size,
                                @Parameter(description = "Sort direction determines how you would like to read the records in ascending or descending order by its createdDate", example = "ASC") @RequestParam String sortDirection) {
        try {
            boolean sortDir = true;
            if(sortDirection == "DESC") sortDir = false;

            ObjectMapper mapper = new ObjectMapper();
            RolesApiResponse response = mapper
                    .convertValue(roleService.getRoles(page, size, sortDir), RolesApiResponse.class);

            return Response.ok(response);
        } catch (Exception err) {
            err.printStackTrace();
            return Response.exception();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response CreateRole(@RequestBody @Valid CreateRole createRole) {
        try {
            Roles role = new Roles();
            role.setName(createRole.getName());
            role.setDescription(createRole.getDescription());
            return Response.ok(roleService.createRole(role));
        }catch (DataIntegrityViolationException err) {
            return Response.duplicateEntity(createRole, err);
        } catch (Exception err) {
            err.printStackTrace();
            return Response.exception();
        }
    }

    @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response AssignRole(@RequestBody @Valid AssignRole assignRole) {
        UserRoles userRoles = null;
        Long defaultRoleId;
        try {
            boolean isValidTeamMember = teamService.isTeamMember(assignRole.getUserId(), assignRole.getTeamId());
            if (!isValidTeamMember) return Response.badRequest();


            userRoles = new UserRoles();
            userRoles.setRoleId(assignRole.getRoleId());
            if (assignRole.getRoleId() == null) {
                defaultRoleId = roleService.findRoleByName(DEFAULT_ROLE).get().getId();
                userRoles.setRoleId(defaultRoleId);
            }
            userRoles.setUserId(assignRole.getUserId());
            userRoles.setTeamId(assignRole.getTeamId());
            roleService.assignRole(userRoles);

            return Response.ok();
        } catch (DataIntegrityViolationException err) {
            return Response.duplicateEntity(userRoles, err);
        } catch (Exception err) {
            err.printStackTrace();
            return Response.exception();
        }
    }


    @GetMapping(value="/users/{userId}/teams/{teamId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getRoleForUserInTeam(@PathVariable String userId, @PathVariable String teamId) {
        try {
            Optional<UserRoles> response = roleService.findByTeamIdAndUserId(teamId, userId);
            if (response.isEmpty()) return Response.notFound();
            Optional<Roles> role = roleService.getRole(response.get().getRoleId());

            return Response.ok(role.get().getName());
        } catch (Exception err) {
            err.printStackTrace();
            return Response.exception();
        }
    }

    @GetMapping(value="/{roleId}/teams/{teamId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getMembersWithRole(@PathVariable Long roleId, @PathVariable String teamId) {
        try {
            List<UserRoles> userRolesList = roleService.findByTeamIdAndRoleId(teamId, roleId);
            return Response.ok(userRolesList);
        } catch (Exception err) {
            err.printStackTrace();
            return Response.exception();
        }
    }
}
