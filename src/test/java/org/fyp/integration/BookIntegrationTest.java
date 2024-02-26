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
public class BookIntegrationTest {
    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );


    @Test
    public void getAllBooks_ShouldReturn200_WhenBooksExist(){
        Response response = APP.client().target("http://localhost:8080/api/books").request()
                .get();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void getBookById_ShouldReturn200_WhenBookExists(){
        Response response = APP.client().target("http://localhost:8080/api/books/1").request()
                .get();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void getBookById_ShouldReturn500_WhenBookDoesNotExist(){
        Response response = APP.client().target("http://localhost:8080/api/books/999").request()
                .get();
        Assertions.assertEquals(404, response.getStatus());
    }
}
