package group.msg.playground.microservice;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("infos")
public class ServerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfos(@Context HttpHeaders headers) {
        System.out.println("getInfos");
        MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();
        JsonObjectBuilder mainObjectBuilder = Json.createObjectBuilder();
        mainObjectBuilder.add("Name", "server");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        requestHeaders.forEach((key, value) -> objectBuilder.add(key, value.toString()));
        mainObjectBuilder.add("RequestHeaders", objectBuilder);
        return Response.ok(mainObjectBuilder.build()).build();
    }
}
