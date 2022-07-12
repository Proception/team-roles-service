package com.tempo.teamroles.service;

import com.tempo.teamroles.entity.Team;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class TeamServiceImpl implements TeamService {
    @Value("${external.microservice.api.teams}")
    private String EXTERNAL_TEAMS_API;

    @Override
    public Team getTeam(String teamId) {
        RestTemplate restTemplate = new RestTemplate();
        Team team = restTemplate.getForObject(EXTERNAL_TEAMS_API+ teamId, Team.class);
        return team;
    }

    @Override
    public boolean isTeamMember(String userId, String teamId) {
        Team team = getTeam(teamId);
        ArrayList<String> teamMembers = team.getTeamMemberIds();
        return teamMembers.contains(userId);
    }
}
