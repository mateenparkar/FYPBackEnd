package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.fyp.cli.CommentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CommentIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getCommentsByBook_ShouldReturn200_WhenBookExists(){
        Response response = APP.client().target("http://localhost:8080/api/comments/1").request()
                .get();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void addComment_ShouldReturn201_WhenCommentIsAdded(){
        CommentRequest commentRequest = new CommentRequest(1, 1, "This is a test comment", new Date(1));
        Response response = APP.client().target("http://localhost:8080/api/comment").request()
                .post(Entity.entity(commentRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(201, response.getStatus());
    }
}
