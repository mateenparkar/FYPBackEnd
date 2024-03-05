package org.fyp;

import ch.qos.logback.core.subst.Token;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.fyp.api.*;
import org.fyp.auth.JWTAuthenticator;
import org.fyp.auth.JWTAuthorizer;
import org.fyp.auth.JWTFilter;
import org.fyp.cli.User;
import org.fyp.db.*;
import org.fyp.resources.*;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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
        JWTAuthenticator jwtAuthenticator = new JWTAuthenticator(new TokenService(new AuthDao(new DatabaseConnector())));

        environment.jersey().register(new AuthDynamicFeature(new JWTFilter.Builder().setAuthenticator(jwtAuthenticator).
                setAuthorizer(new JWTAuthorizer()).setPrefix("Bearer").buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(new AuthController(new AuthService(new AuthDao(new DatabaseConnector()),
                new TokenService(new AuthDao(new DatabaseConnector())))));

        environment.jersey().register(new BooksController(new BookService(new BookDao(new DatabaseConnector()))));
        environment.jersey().register(new AuthorController(new AuthorService(new AuthorDao(new DatabaseConnector()))));
        environment.jersey().register(new GenreController(new GenreService(new GenreDao(new DatabaseConnector()))));
        environment.jersey().register(new UserBooksController(new UserBooksService(new UserBooksDao(new DatabaseConnector()))));
        environment.jersey().register(new UserBooksController(new UserBooksService(new UserBooksDao(new DatabaseConnector()))));
        environment.jersey().register(new CommentController(new CommentService(new CommentDao(new DatabaseConnector()))));
        environment.jersey().register(new FriendsController(new FriendsService(new FriendsDao(new DatabaseConnector()))));
        environment.jersey().register(new UserController(new UserService(new UserDao(new DatabaseConnector()))));
    }

}
