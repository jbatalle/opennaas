package org.opennaas.gui.nfvrouting.services.rest.routing;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.extensions.vrf.utils.Utils;
import org.opennaas.gui.nfvrouting.entities.route.Route;
import org.opennaas.gui.nfvrouting.services.rest.GenericRestService;
import org.opennaas.gui.nfvrouting.services.rest.RestServiceException;
import org.opennaas.gui.nfvrouting.utils.Constants;

/**
 * @author Josep Batallé (josep.batalle@i2cat.net)
 * TODO: 
 * - remove SDN_Network
 * - remove or adapt getSwInfo(), requires switchName used in OpenNaaS
 * - obtain switchName before (in the controller/javascript????)
 * - extract from the NetTopology?
 */
public class VNFService extends GenericRestService {

    private static final Logger LOGGER = Logger.getLogger(VNFService.class);
   

    public String duplicateVNF(String name, String ip) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL("vrf/staticrouting/duplicateVNF/" + name + "/" + ip);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response.getEntity(String.class);
    }
}
