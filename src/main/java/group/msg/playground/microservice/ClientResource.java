package group.msg.playground.microservice;

import group.msg.playground.microservice.client.InfoResource;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("client")
public class ClientResource {

    private static final Logger logger = Logger.getLogger(ClientResource.class.getName());

    @Inject
    @RestClient
    private InfoResource infoResource;

    @Inject
    @ConfigProperty(name = "org.eclipse.microprofile.rest.client.propagateHeaders", defaultValue = "n/a")
    private String propagation;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response performRemoteServiceCall(@Context HttpHeaders headers, @HeaderParam(value = "Accept-Language") String aLang) {
        Config config = ConfigProvider.getConfig();
        Iterable<ConfigSource> configSources = config.getConfigSources();
        MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        JsonObjectBuilder mainObjectBuilder = Json.createObjectBuilder();
        mainObjectBuilder.add("Name", "client");
        mainObjectBuilder.add("propagationConfig", propagation);
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        requestHeaders.forEach((key, value) -> objectBuilder.add(key, value.toString()));
        mainObjectBuilder.add("RequestHeaders", objectBuilder);
        Response infosResponse = infoResource.getInfos();
        mainObjectBuilder.add("ServerResponse", infosResponse.readEntity(JsonObject.class));
        return Response.ok(mainObjectBuilder.build()).build();
    }


}
