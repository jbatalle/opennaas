package org.opennaas.gui.nfvrouting.services.rest.routing;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.opennaas.gui.nfvrouting.services.rest.GenericRestService;

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
   

    public String duplicateVNF(String name, String CtrlIP) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL("vrf/routemgt/duplicateVNF/" + name + "/" + CtrlIP, 1);
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

    public String copyRoutesToOtherVNF(int i) {
        ClientResponse response;
        String vnfName = "VNF1";
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL2("vrf/routemgt/importRoutes/"+vnfName);
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
    
    public String setVRFControllers(int vnf, String CtrlIP, String vnfName) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL("vrf/routemgt/setVRFControllers/" + CtrlIP + "/" + vnfName, vnf);
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
