package com.tempo.teamroles.validators;

import com.tempo.teamroles.entity.User;
import com.tempo.teamroles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUserValidator implements ConstraintValidator<ValidUserId, String> {
    private UserService userService;

    @Autowired
    public ValidUserValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public boolean isValid(String userId, ConstraintValidatorContext constraintValidatorContext) {

        if (userId == null) return false;
        if (userId == "") return false;

        User user = userService.getUser(userId);

        if (user != null) return true;

        return false;
    }
}
