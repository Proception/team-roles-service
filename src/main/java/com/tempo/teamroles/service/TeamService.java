package com.tempo.teamroles.service;

import com.tempo.teamroles.entity.Team;

public interface TeamService {
    Team getTeam(String teamId);
    boolean isTeamMember(String userId, String teamId);
}
