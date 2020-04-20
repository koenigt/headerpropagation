# Sample Project to show Header Propagation Feature of Microprofile
This is a straight forward JAX-RS application using Microprofile RestClient with two JAX-RS Resources to show how the
*header propagation feature* should work.

The application might be deployed on a single or two different servers because it contains a ClientResources as well as
a ServerResource. The idea is that the ClientResources is called by an single page app which has some authentication done.
The token is sent to the ClientResource in the HTTP Header and must be propagated when the ClientResource makes a call to 
the ServerResource.

ClientResource and ServerResource are packing the incoming Headers & Values in the the response so it is easy to find out
if the propagation was done or not.

1. You might build the WAR using Maven: *mvn clean package*
2. Start the server like openliberty and copy the WAR from *target\mp-headerpropagation-1.0.0-SNAPSHOT.war* to *\wlp_20.0.0.4\usr\servers\defaultServer\dropins\mp-headerpropagation-1.0.0-SNAPSHOT.war*
3. Open the browser to see if the application deployment worked fine, e.g. http://localhost:9080/mp-hp
4. use CURL to see if the propagation works as expected: *curl -i -H "Accept: application/json" -H "Header2Propagate: PropagatedValue" http://localhost:9080/mp-hp/api/client*