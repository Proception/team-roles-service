package com.tempo.teamroles.service;

import com.tempo.teamroles.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Value("${external.microservice.api.users}")
    private String EXTERNAL_USERS_API;

    @Override
    public User getUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(EXTERNAL_USERS_API+ userId, User.class);
        return user;
    }
}
