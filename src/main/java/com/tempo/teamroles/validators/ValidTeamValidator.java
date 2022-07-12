package com.tempo.teamroles.validators;

import com.tempo.teamroles.entity.Team;
import com.tempo.teamroles.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTeamValidator implements ConstraintValidator<ValidTeamId, String> {
    private TeamService teamService;

    @Autowired
    public ValidTeamValidator(TeamService teamService) {
        this.teamService = teamService;
    }
    @Override
    public boolean isValid(String teamId, ConstraintValidatorContext constraintValidatorContext) {
        if (teamId == null) return false;
        if (teamId == "") return false;

        Team team = teamService.getTeam(teamId);
        if (team != null) return true;

        return false;
    }
}
