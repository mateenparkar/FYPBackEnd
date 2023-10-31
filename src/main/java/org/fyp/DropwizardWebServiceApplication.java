package org.fyp;

import ch.qos.logback.core.subst.Token;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.fyp.api.AuthService;
import org.fyp.api.BookService;
import org.fyp.api.TokenService;
import org.fyp.db.AuthDao;
import org.fyp.db.BookDao;
import org.fyp.db.DatabaseConnector;
import org.fyp.resources.AuthController;
import org.fyp.resources.BooksController;

public class DropwizardWebServiceApplication extends Application<DropwizardWebServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardWebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardWebService";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardWebServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<DropwizardWebServiceConfiguration>(){
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(DropwizardWebServiceConfiguration configuration){
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final DropwizardWebServiceConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new AuthController(new AuthService(new AuthDao(new DatabaseConnector()),
                new TokenService(new AuthDao(new DatabaseConnector())))));

        environment.jersey().register(new BooksController(new BookService(new BookDao(new DatabaseConnector()))));

    }

}
