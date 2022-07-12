package com.tempo.teamroles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tempo.teamroles.controller.v1.request.AssignRole;
import com.tempo.teamroles.controller.v1.request.CreateRole;
import com.tempo.teamroles.dto.response.Response;
import com.tempo.teamroles.dto.response.RolesApiResponse;
import com.tempo.teamroles.entity.Roles;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamRolesServiceTests {

    @Test
    @Order(1)
    void contextLoads() {
    }

    @Test
    @Order(2)
    public void
    shouldCreateRoleAndReturnOk() {
        RestTemplate restTemplate = new RestTemplate();
        CreateRole createRole = new CreateRole();
        createRole.setName("Manager");
        createRole.setDescription("Manages Apps");
        Response response = restTemplate.postForObject("http://localhost:8080/api/v1/roles", createRole, Response.class);

        ObjectMapper mapper = new ObjectMapper();
        Roles role = mapper.convertValue(response.getPayload(), Roles.class);

        assertEquals(response.getStatus().name(), "OK");
        assertEquals(role.getName(), createRole.getName());
        assertEquals(role.getDescription(), createRole.getDescription());
    }

    @Test
    @Order(3)
    public void
    shouldReturnRoleInDatabase() {
        RestTemplate restTemplate = new RestTemplate();
        CreateRole createRole = new CreateRole();
        createRole.setName("Product Owner");
        createRole.setDescription("Manages Product");
        Response response = restTemplate.getForObject("http://localhost:8080/api/v1/roles?page=0&size=3&sortDirection=ASC", Response.class);

        System.out.println(response.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        RolesApiResponse apiResponse = mapper.convertValue(response.getPayload(), new TypeReference<>() {});
        ArrayList<Roles> roles = mapper.convertValue(apiResponse.getContent(), new TypeReference<ArrayList<Roles>>(){});

        assertEquals(response.getStatus().name(), "OK");
        assertEquals(roles.size(), 1);
    }

    @Test
    @Order(4)
    public void
    shouldAssignDefaultRoleAndReturnOk() {
        RestTemplate restTemplate = new RestTemplate();

        CreateRole createRole = new CreateRole();
        createRole.setName("Developer");
        createRole.setDescription("Writes Code");
        restTemplate.postForObject("http://localhost:8080/api/v1/roles", createRole, Response.class);

        AssignRole assignRole = new AssignRole();
        assignRole.setUserId("b047d3f4-3469-47ce-a03f-1637a6de036b");
        assignRole.setTeamId("7676a4bf-adfe-415c-941b-1739af07039b");

        Response assignRoleResponse = restTemplate.postForObject("http://localhost:8080/api/v1/roles/assign", assignRole, Response.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Response> userRoleResponse = restTemplate
                .exchange("http://localhost:8080/api/v1/roles/users/b047d3f4-3469-47ce-a03f-1637a6de036b/teams/7676a4bf-adfe-415c-941b-1739af07039b", HttpMethod.GET, requestEntity, Response.class);

        ObjectMapper mapper = new ObjectMapper();
        String role = mapper.convertValue(userRoleResponse.getBody().getPayload(), String.class);

        assertEquals(assignRoleResponse.getStatus().name(), "OK");
        assertEquals(userRoleResponse.getBody().getStatus().name(), "OK");
        assertEquals(role, "Developer");
    }

    @Test
    @Order(5)
    public void
    shouldAssignSpecifiedRoleAndReturnOk() {
        RestTemplate restTemplate = new RestTemplate();

        CreateRole createRole = new CreateRole();
        createRole.setName("Tester");
        createRole.setDescription("Tests Code");
        Response createRoleResponse = restTemplate.postForObject("http://localhost:8080/api/v1/roles", createRole, Response.class);

        ObjectMapper mapper = new ObjectMapper();
        Roles role = mapper.convertValue(createRoleResponse.getPayload(), Roles.class);

        AssignRole assignRole = new AssignRole();
        assignRole.setUserId("fc492a1c-2e5a-4d84-a75b-ad1ed82f09d6");
        assignRole.setTeamId("2ab857e2-1b5f-490e-a0be-9f8512fae1b7");
        assignRole.setRoleId(role.getId());

        Response assignRoleResponse = restTemplate.postForObject("http://localhost:8080/api/v1/roles/assign", assignRole, Response.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Response> userRoleResponse = restTemplate
                .exchange("http://localhost:8080/api/v1/roles/users/"+ assignRole.getUserId() +"/teams/" + assignRole.getTeamId(), HttpMethod.GET, requestEntity, Response.class);

        String roleName = mapper.convertValue(userRoleResponse.getBody().getPayload(), String.class);

        assertEquals(assignRoleResponse.getStatus().name(), "OK");
        assertEquals(userRoleResponse.getBody().getStatus().name(), "OK");
        assertEquals(roleName, createRole.getName());
    }

}
