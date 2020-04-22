package group.msg.playground.microservice;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationPath("api")
public class ServerServiceApplication extends Application {

    private static final Logger logger = Logger.getLogger(ServerServiceApplication.class.getName());


    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(ServerResource.class);
        classes.add(ClientResource.class);
        return classes;
    }
}
