package com.tempo.teamroles.controller.v1.request;

import com.tempo.teamroles.validators.ValidRoleId;
import com.tempo.teamroles.validators.ValidTeamId;
import com.tempo.teamroles.validators.ValidUserId;

public class AssignRole {

    @ValidRoleId
    private Long roleId;

    @ValidUserId
    private String userId;

    @ValidTeamId
    private String teamId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
