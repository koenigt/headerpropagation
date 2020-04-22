package group.msg.playground.microservice;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("infos")
public class ServerResource {
	
	private static final Logger logger = Logger.getLogger(ServerResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfos(@Context HttpHeaders headers) {
		logger.info("ServerResource called");
        MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        JsonObjectBuilder mainObjectBuilder = Json.createObjectBuilder();
        mainObjectBuilder.add("Name", "ServerResource");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        requestHeaders.forEach((key, value) -> objectBuilder.add(key, value.toString()));
        mainObjectBuilder.add("IncomingRequestHeaders", objectBuilder);
        return Response.ok(mainObjectBuilder.build()).build();
    }
}
