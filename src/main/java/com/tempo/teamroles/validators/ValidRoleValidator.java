package com.tempo.teamroles.validators;

import com.tempo.teamroles.entity.Roles;
import com.tempo.teamroles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ValidRoleValidator implements ConstraintValidator<ValidRoleId, Long> {
    private RoleService roleService;

    @Autowired
    public ValidRoleValidator (RoleService roleService) {
        this.roleService = roleService;
    }
    @Override
    public boolean isValid(Long roleId, ConstraintValidatorContext constraintValidatorContext) {
        if (roleId == null) return true;

        Optional<Roles> role = roleService.getRole(roleId);
        if(role.isPresent()) return true;

        return false;
    }
}
