package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthorIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getAuthorById_ShouldReturn200_WhenAuthorExists(){
        Response response = APP.client().target("https://fyp.mateenparkar.xyz/api/author/1").request()
                .get();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void getAuthorById_ShouldReturn404_WhenAuthorDoesNotExist(){
        Response response = APP.client().target("https://fyp.mateenparkar.xyz/api/author/999").request()
                .get();

        Assertions.assertEquals(500, response.getStatus());
    }
}
