package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.checkerframework.checker.units.qual.A;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.fyp.cli.UserBooksRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UserBooksIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getUserBooks_ShouldReturn200_WhenUserBooksExists(){
        Response response = APP.client().target("http://localhost:8080/api/getUserBooks/1").request().get();

        Assertions.assertEquals(200, response.getStatus());
    }

//    @Test
//    public void addUserBooks_ShouldReturn200_WhenUserBooksAdded(){
//        UserBooksRequest userBooksRequest = new UserBooksRequest(1,1);
//        Response response = APP.client().target("http://localhost:8080/api/addBookToUser").request().post(Entity.entity(userBooksRequest, MediaType.APPLICATION_JSON_TYPE));
//        Assertions.assertEquals(200, response.getStatus());
//    }
//
//    @Test
//    public void updateUserBooks_ShouldReturn200_WhenUserBooksUpdated(){
//        UserBooksRequest userBooksRequest = new UserBooksRequest(1,1, "READ", 5, new Date(2021, 1, 1));
//        Response response = APP.client().target("http://localhost:8080/api/updateBookForUser").request().put(Entity.entity(userBooksRequest, MediaType.APPLICATION_JSON_TYPE));
//        Assertions.assertEquals(200, response.getStatus());
//    }
}
