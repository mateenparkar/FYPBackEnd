package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.fyp.cli.FriendRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class FriendsIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );


    @Test
    public void getFriends_ShouldReturn200_WhenFriendsExist(){
        Response response = APP.client().target("http://localhost:8080/api/getFriends/1").request()
                .get();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void addFriend_ShouldReturn201_WhenFriendIsAdded(){
        FriendRequest friendRequest = new FriendRequest(1, 4);
        Response response = APP.client().target("http://localhost:8080/api/addFriend").request()
                .post(Entity.entity(friendRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(201, response.getStatus());
    }
}
