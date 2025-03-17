package org.fyp.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.fyp.DropwizardWebServiceApplication;
import org.fyp.DropwizardWebServiceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class GenreIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getGenreById_ShouldReturn200_WhenGenreExists() {
        Response response = APP.client().target("https://fyp.mateenparkar.xyz/api/genre/1").request().get();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void getGenreById_ShouldReturn500_WhenGenreDoesNotExist() {
        Response response = APP.client().target("https://fyp.mateenparkar.xyz/api/genre/100").request().get();
        Assertions.assertEquals(500, response.getStatus());
    }
}
