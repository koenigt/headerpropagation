package group.msg.playground.microservice.client;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient(baseUri = "http://localhost:9080/mp-hp")
//@RegisterRestClient()
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@RegisterClientHeaders()
//@RegisterClientHeaders(MyDefaultClientHeadersFactory.class)
public interface InfoResource {


    @GET
    @Path("infos")
    @ClientHeaderParam(name="MyClientHeader", value = "newHeaderValue")
    @Retry()
    Response getInfos();

}
