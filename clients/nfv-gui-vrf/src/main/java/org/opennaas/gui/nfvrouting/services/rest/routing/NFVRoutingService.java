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
public class NFVRoutingService extends GenericRestService {

    private static final Logger LOGGER = Logger.getLogger(NFVRoutingService.class);
    private static final String sdn = Constants.SDN_RESOURCE;
    private static final String genericNetwork = Constants.GENERICNETWORK_RESOURCE;

    /**
     * Call a rest service to get the Route Table of the virtualized router
     *
     * @param type of IP version
     * @return true if the environment has been created
     * @throws RestServiceException
     */
    public String getRouteTable(int type, int vnf) throws RestServiceException {
        ClientResponse response;

        try {
            LOGGER.info("Calling get Route Table service");
            String url = getURL("vrf/routemgt/routes/" + type, vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
            LOGGER.info("Route table: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS is not started";
//            throw e;
        }
        return checkResponse(response) ? response.getEntity(String.class) : null;
    }

    /**
     * Call a rest service to insert a Route
     *
     * @param route
     * @return true if the environment has been created
     */
    public String insertRoute(Route route, int vnf) {
        String response = null;
        try {
            LOGGER.info("Calling insert Route Table service");
            String url = getURL("vrf/routemgt/route", vnf);
            Form fm = new Form();
            fm.add("ipSource", route.getSourceAddress());
            fm.add("ipDest", route.getDestinationAddress());
            fm.add("switchDPID", route.getSwitchInfo().getMacAddress());
            fm.add("inputPort", route.getSwitchInfo().getInputPort());
            fm.add("outputPort", route.getSwitchInfo().getOutputPort());
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).put(String.class, fm);
            LOGGER.info("Route table: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }

    /**
     * Remove Route given the id
     *
     * @param id
     * @param version
     * @param vnf
     * @return
     */
    public String deleteRoute(int id, int version, int vnf) {
        String response = null;
        try {
            LOGGER.error("Remove route "+id+". Version IPv"+version+" "+Integer.toString(id));
            String url = getURL("vrf/routemgt/route", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            webResource.queryParam("id", Integer.toString(id)).queryParam("version", Integer.toString(version)).delete();
            LOGGER.error("Removed route: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }
    
    /**
     * Remove Route given the id
     *
     * @param vnf
     * @return
     */
    public String deleteAllRoutes(int vnf) {
        String response = null;
        try {
            LOGGER.error("Remove all route");
            String url = getURL("vrf/routemgt/routes", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            webResource.accept(MediaType.TEXT_PLAIN).delete();
            LOGGER.error("Removed route: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }

    /**
     * Information about the switch.
     *
     * @param resourceName
     * @return Flow table of the switch.
     */
    public String getFlowTable(String resourceName, int vnf) {
        String response = null;
        try {
            LOGGER.info("Calling get Flow Table");
            String url = getURL("openflowswitch/" + resourceName + "/offorwarding/getOFForwardingRules", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(String.class);
            LOGGER.info("Flow Table: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }

    /**
     * Get specific route.
     * @param ipSrc
     * @param ipDst
     * @param dpid
     * @param inPort
     * @return 
     */
    public String getRoute(String ipSrc, String ipDst, String dpid, String inPort, int vnf) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL("vrf/staticrouting/route/" + Utils.StringIPv4toInt(ipSrc) + "/" + Utils.StringIPv4toInt(ipDst) + "/" + dpid + "/" + inPort + "/true", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
            LOGGER.info("Log....: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response.getEntity(String.class);
    }

    /**
     * Insert Route from javascript
     * 
     * @param ipSrc
     * @param ipDst
     * @param dpid
     * @param srcPort
     * @param dstPort
     * @return 
     */
    public String insertRoute(String ipSrc, String ipDst, String dpid, String srcPort, String dstPort, int vnf) {
        String response = null;
        try {
            LOGGER.info("Calling insert Route Table service");
            String url = getURL("vrf/routemgt/route", vnf);
            Form fm = new Form();
            fm.add("ipSource", ipSrc);
            fm.add("ipDest", ipDst);
            fm.add("switchDPID", dpid);
            fm.add("inputPort", srcPort);
            fm.add("outputPort", dstPort);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).put(String.class, fm);
            LOGGER.error("Inserted? : " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
//            throw e;
        }
        return response;
    }

    /**
     * Get route taken into account only the IP addresses
     * @param ipSrc
     * @param ipDst
     * @param vnf
     * @return 
     */
    public String getRoute(String ipSrc, String ipDst, int vnf) {
        LOGGER.error("SERVICE GET ROUTE");
        ClientResponse response;
        String resp;
        try {
            String url = getURL("vrf/routemgt/routes/4/" + Utils.StringIPv4toInt(ipSrc) + "/" + Utils.StringIPv4toInt(ipDst), vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
            resp = response.getEntity(String.class);
            LOGGER.error("Response: " + response.getEntity(String.class));
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return resp;
//        return response.getEntity(String.class);
    }
    
    //---------------------DEMO -- to remove
    public String getLog(int vnf) {
        String response;
        try {
            LOGGER.info("Get log of OpenNaaS");
            String url = getURL("vrf/staticrouting/log", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
            LOGGER.info("Log....: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response;
    }
    public String getStream(int vnf) {
        String response;
        try {
            LOGGER.info("Get stream info to OpenNaaS");
            String url = getURL("vrf/staticrouting/stream", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
            LOGGER.info("Stream....: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response;
    }

    public String getONRouteMode(int vnf) {
        String response;
        try {
            LOGGER.info("Get stream info to OpenNaaS");
            String url = getURL("vrf/routing/routeMode", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
            LOGGER.info("Stream....: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response;
    }
    
    public String setONRouteMode(String mode, int vnf) {
        String response;
        try {
            LOGGER.info("Get stream info to OpenNaaS");
            String url = getURL("vrf/routing/routeMode/"+mode, vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).get(String.class);
            LOGGER.info("Stream....: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response;
    }
    
    /**
     * Obtain OpenNaaS generic network
     * @return
     * @throws RestServiceException 
     */
    public Topology getTopology(int vnf) throws RestServiceException{
        ClientResponse response;
        try {
            LOGGER.info("Calling get Topology");
            String url = getURL("genericnetwork/"+genericNetwork+"/nettopology/topology", vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return checkResponse(response) ? response.getEntity(Topology.class) : null;
    }
    
    public String setGenNetResource(String resourceName, int vnf) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = getURL("vrf/staticrouting/gennetres/" + resourceName, vnf);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.TEXT_PLAIN).put(ClientResponse.class);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            return "OpenNaaS not started";
        }
        return response.getEntity(String.class);
    }

    public String cleanControllers(String ctrl, String dpid) {
        ClientResponse response;
        try {
            LOGGER.info("Get Route to OpenNaaS");
            String url = "http://"+ctrl+":8080/wm/staticflowentrypusher/clear/"+dpid+"/json";
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
