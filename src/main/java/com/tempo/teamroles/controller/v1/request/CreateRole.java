package com.tempo.teamroles.controller.v1.request;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateRole {
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
