package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.fyp.cli.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void login_ShouldReturnCode200(){
        LoginRequest loginRequest = new LoginRequest("user", "password");

        Response response = APP.client().target("http://localhost:8080/api/login").request()
                .post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void login_ShouldReturn401(){
        LoginRequest loginRequest = new LoginRequest("user", "wrongpassword");

        Response response = APP.client().target("http://localhost:8080/api/login").request()
                .post(Entity.entity(loginRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(401, response.getStatus());
    }
}
