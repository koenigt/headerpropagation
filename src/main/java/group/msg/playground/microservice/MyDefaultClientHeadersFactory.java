package group.msg.playground.microservice;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDefaultClientHeadersFactory implements ClientHeadersFactory {

    public final static String PROPAGATE_PROPERTY = "org.eclipse.microprofile.rest.client.propagateHeaders";
    private final static String CLASS_NAME = MyDefaultClientHeadersFactory.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private static Optional<Config> config() {
        try {
            return Optional.ofNullable(ConfigProvider.getConfig());
        }
        catch (ExceptionInInitializerError | NoClassDefFoundError | IllegalStateException ex) {
            // expected if no MP Config implementation is available
            return Optional.empty();
        }
    }

    private static Optional<String> getHeadersProperty() {
        Optional<Config> c = config();
        if (c.isPresent()) {
            return Optional.ofNullable(c.get().getOptionalValue(PROPAGATE_PROPERTY, String.class).orElse(null));
        }
        return Optional.empty();
    }

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {

        LOG.entering(CLASS_NAME, "update", new Object[]{incomingHeaders, clientOutgoingHeaders});

        MultivaluedMap<String, String> propagatedHeaders = new MultivaluedHashMap<>();
        Optional<String> propagateHeaderString = getHeadersProperty();
        if (propagateHeaderString.isPresent()) {
            Arrays.stream(propagateHeaderString.get().split(","))
                    .forEach( header -> {
                        if (incomingHeaders.containsKey(header)) {
                            propagatedHeaders.put(header, incomingHeaders.get(header));
                        }
                    });
        }
        propagatedHeaders.putAll(clientOutgoingHeaders);

        LOG.exiting(CLASS_NAME, "update", propagatedHeaders);

        return propagatedHeaders;
    }
}
