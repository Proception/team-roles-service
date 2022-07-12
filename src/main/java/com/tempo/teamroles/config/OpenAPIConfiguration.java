package com.tempo.teamroles.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Team Roles Service",
                description = "The purpose of this service is to expose APIs with the intention of enriching the users service and teams services by providing the functionality to create roles, and associating them with members in a team.\n",
                contact = @Contact(
                        name = "Benedict Esimaje",
                        url = "http://www.benedictesimaje.com",
                        email = "omasan.esimaje@gmail.com"
                ),
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/Proception/tempo-backend")),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenAPIConfiguration {
}
