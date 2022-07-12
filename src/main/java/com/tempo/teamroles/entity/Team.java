package com.tempo.teamroles.entity;

import java.util.ArrayList;

public class Team {

    private String id;
    private String name;
    private String teamLeadId;
    private ArrayList<String> teamMemberIds;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(String teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public ArrayList<String> getTeamMemberIds() {
        return teamMemberIds;
    }

    public void setTeamMemberIds(ArrayList<String> teamMemberIds) {
        this.teamMemberIds = teamMemberIds;
    }
}
