# Sample Project to show Header Propagation Feature of Microprofile
This is a straight forward JAX-RS application using Microprofile RestClient with two JAX-RS Resources to show how the
**header propagation feature** should work.

## What the sample is about
The application might be deployed on a single or two different servers. It contains a
[ClientResources](src/main/java/group/msg/playground/microservice/ClientResource.java) as well
as a [ServerResource](src/main/java/group/msg/playground/microservice/ServerResource.java). The idea is that the
ClientResources is called by a client (e.g. single page app) which has some authentication done and
a token is used and must be sent via HTTP Header. Therefor it should be propagated when the ClientResource makes
calls to other JAX-RS Resources like the ServerResource.

In this sample the ClientResource and ServerResource are adding the incoming Headers & Values in the the response so it is easy to find out
if the propagation was done or not. 

## Build/deploy and run the sample
1. You might build the WAR using Maven: `mvn clean package`
2. Start the server like openliberty and copy the WAR from *target\mp-headerpropagation-1.0.0-SNAPSHOT.war* to *\wlp_20.0.0.4\usr\servers\defaultServer\dropins\mp-headerpropagation-1.0.0-SNAPSHOT.war*
3. Open the browser to see if the application deployment worked fine, e.g. http://localhost:9080/mp-hp
4. use CURL to see if the propagation works as expected: `curl -i -H "Accept: application/json" -H "Header2Propagate: PropagatedValue" http://localhost:9080/mp-hp/api/client`

## Run the sample on different Servers
If you want to deploy the application on two different servers you must adjust the URI for the RestClient
which will be calling the ServerResource on a different server. Check the [microprofile config file](src/main/webapp/META-INF/microprofile-config.properties) and
adjust the line `group.msg.playground.microservice.client.InfoResource/mp-rest/uri=http://localhost:9080/mp-hp`