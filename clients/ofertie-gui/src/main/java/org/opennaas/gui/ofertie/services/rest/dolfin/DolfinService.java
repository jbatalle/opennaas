package org.opennaas.gui.ofertie.services.rest.dolfin;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.opennaas.extensions.genericnetwork.capability.nclprovisioner.api.CircuitCollection;
import org.opennaas.extensions.genericnetwork.model.portstatistics.TimePeriod;
import org.opennaas.gui.ofertie.services.rest.GenericRestService;
import org.opennaas.gui.ofertie.services.rest.RestServiceException;
import org.opennaas.gui.ofertie.utils.Constants;
import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.extensions.ofertie.ncl.provisioner.api.wrapper.QoSPolicyRequestsWrapper;
import org.opennaas.extensions.openflowswitch.capability.controllerinformation.model.HealthState;
import org.opennaas.extensions.openflowswitch.capability.controllerinformation.model.MemoryUsage;

/**
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
public class DolfinService extends GenericRestService {

    private static final Logger LOGGER = Logger.getLogger(DolfinService.class);
    private static final String genericNetwork = Constants.GENERICNETWORK_RESOURCE;

    /**
     *
     * @return
     * @throws org.opennaas.gui.ofertie.services.rest.RestServiceException
     */
    public CircuitCollection getAllocatedCircuits() throws RestServiceException {
        ClientResponse response;
        try {
            LOGGER.info("Calling get Allocated Circuits");
            String url = getURL("genericnetwork/"+genericNetwork+"/nclprovisioner");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            LOGGER.error("Circuits: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return checkResponse(response) ? response.getEntity(CircuitCollection.class) : null;
    }

    /**
     *
     * @return
     * @throws RestServiceException
     */
    public Topology getTopology() throws RestServiceException{
        ClientResponse response = null;
        try {
            LOGGER.info("Calling get Topology");
            String url = getURL("genericnetwork/"+genericNetwork+"/nettopology/topology");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            LOGGER.error("Topology: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
//            throw e;
        }
        return checkResponse(response) ? response.getEntity(Topology.class) : null;
    }

    /**
     * Information about the switch.
     * @param switchName
     * @return Flow table of the switch.
     */
    public String getAllocatedFlows(String switchName) {
        String response = null;
        try {
            LOGGER.info("Calling get Controller Status");
            LOGGER.error("Calling sw INFO");
            String url = getURL("openflowswitch/" + switchName + "/offorwarding/getOFForwardingRules");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(String.class);
            LOGGER.info("Controller status: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }

    public String allocateFlow(String flow) {
        ClientResponse response = null;
        String result = "";
        try {
            LOGGER.info("Calling get Controller Status");
            String url = getURL("ofertie/ncl/flows");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML).post(ClientResponse.class, flow);
            LOGGER.info("Controller status: " + response);
            LOGGER.error("Resource ID: "+response);
            LOGGER.error("Status: "+response.getStatus());
            if(response.getStatus() == (500)){
                result = String.valueOf(response.getStatus());
            }else{
                result = response.getEntity(String.class);
            }
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Unauthorized");
            result = String.valueOf(response.getStatus());
        }
        LOGGER.error(result);
        return result;
    }

    public QoSPolicyRequestsWrapper getAllocatedFlow() {
        QoSPolicyRequestsWrapper response = null;
        try {
            LOGGER.info("Calling get Controller Status");
            String url = getURL("ofertie/ncl/flows");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.get(QoSPolicyRequestsWrapper.class);
            LOGGER.info("Allocated Flow: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Unauthorized");
        }
        return response;
    }

    public String deallocateFlow(String keyId) {
        String response = null;
        try {
            LOGGER.info("Calling get Controller Status");
            String url = getURL("ofertie/ncl/flows/"+keyId);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.delete(String.class);
            LOGGER.info("Deallocated flow: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Jersey: "+e.getMessage());
            response = "";
        }
        return response;
    }


    public String getPortStatistics(TimePeriod period) {
        LOGGER.error("GEtPort");
        String response = "";
        try {
            LOGGER.info("Calling get Controller Status");
            String url = getURL("genericnetwork/"+genericNetwork+"/nclmonitoring");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).post(String.class, period);
            LOGGER.info("Port Statistics: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Jersey: "+e.getMessage());
            response = "";
        }
        return response;
    }

    public String getPortStatistics(TimePeriod period, String switchId) {
        String response = "";
        try {
            LOGGER.error("Calling get Controller Status "+switchId);
            String url = getURL("genericnetwork/"+genericNetwork+"/nclmonitoring/"+switchId);
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(String.class, period);
            LOGGER.info("Port Statistics: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Jersey: "+e.getMessage());
            response = "e.getMessage()";
        }
        return response;
    }

    public String getCircuitStatistics(TimePeriod period) {
        String response = "";
        try {
            LOGGER.info("Calling get Controller Status");
            String url = getURL("genericnetwork/"+genericNetwork+"/circuitstatistics");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
LOGGER.error("IS UPDATED");
            response = webResource.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_PLAIN).post(String.class, period);
            LOGGER.info("Circuit Statistics: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            LOGGER.error("Jersey: "+e.getMessage());
            response = "";
        }
        return response;

    }

    public MemoryUsage getControllerMemoryUsage(String switchName) {
        LOGGER.error("Calling get Memory Usage");
        MemoryUsage response = null;
        String[] el = switchName.split(":");
        if(el.length > 0)
            switchName = el[1];
        try {
            String url = getURL("openflowswitch/" + switchName + "/controllerinformation/memoryUsage");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(MemoryUsage.class);
            LOGGER.error("Controller status: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return response;
    }

    public HealthState getHealthState(String switchName) {
        HealthState response = null;
        String[] el = switchName.split(":");
        LOGGER.error("Calling get Health State");
        if(el.length > 0)
            switchName = el[1];
        try {
            String url = getURL("openflowswitch/" + switchName + "/controllerinformation/healthState");
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).get(HealthState.class);
            LOGGER.error("Controller status: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error("Error: "+e.getMessage());
            throw e;
        }
        return response;
    }
    
     public String setPath(String pathFinderUrl, String xml) {
        String response = null;
        LOGGER.error("Calling set Path");

        try {
            String url = pathFinderUrl;
            Client client = Client.create();
            addHTTPBasicAuthentication(client);
            WebResource webResource = client.resource(url);
            response = webResource.accept(MediaType.APPLICATION_XML).post(String.class, xml);
            LOGGER.error("Controller status: " + response);
        } catch (ClientHandlerException e) {
            LOGGER.error("Error: "+e.getMessage());
            throw e;
        }
        return response;
    }
}

