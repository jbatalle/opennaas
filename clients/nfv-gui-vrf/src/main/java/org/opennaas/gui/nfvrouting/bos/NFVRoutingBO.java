package org.opennaas.gui.nfvrouting.bos;

import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.gui.nfvrouting.entities.route.Route;
import org.opennaas.gui.nfvrouting.services.rest.RestServiceException;
import org.opennaas.gui.nfvrouting.services.rest.routing.NFVRoutingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Josep
 */
public class NFVRoutingBO {

    @Autowired
    private NFVRoutingService nfvRoutingService;

    /**
     * Obtain a route table given the IP type
     *
     * @param type
     * @return json that contains the specified route table
     * @throws RestServiceException
     */
    public String getRouteTable(int type, int vnf) throws RestServiceException {
        return nfvRoutingService.getRouteTable(type, vnf);
    }

    /**
     * Insert new route in the OpenNaaS model
     *
     * @param route
     * @return status
     */
    public String insertRoute(Route route, int vnf) {
        return nfvRoutingService.insertRoute(route, vnf);
    }

    /**
     * Remove route given the id
     *
     * @param id
     * @param version
     * @return status
     */
    public String deleteRoute(int id, int version, int vnf) {
        return nfvRoutingService.deleteRoute(id, version, vnf);
    }
    /**
     * Remove route given the id
     *
     * @return status
     */
    public String deleteAllRoutes(int vnf) {
        return nfvRoutingService.deleteAllRoutes(vnf);
    }

    /**
     * Request the status of specific controller
     *
     * @param resourceName
     * @return Offline or Online
     */
    public String getFlowTable(String resourceName, int vnf) {
        return nfvRoutingService.getFlowTable(resourceName, vnf);
    }

    public String getRoute(String ipSrc, String ipDst, String dpid, String inPort, int vnf) {
        return nfvRoutingService.getRoute(ipSrc, ipDst, dpid, inPort, vnf);
    }

    public String insertRoute(String ipSrc, String ipDst, String dpid, String srcPort, String dstPort, int vnf) {
        return nfvRoutingService.insertRoute(ipSrc, ipDst, dpid, srcPort, dstPort, vnf);
    }

    public String getRoute(String srcIP, String dstIP, int vnf) {
        return nfvRoutingService.getRoute(srcIP, dstIP, vnf);
    }

    public String getONRouteMode(int vnf) {
        return nfvRoutingService.getONRouteMode(vnf);
    }
    
    public String setONRouteMode(String mode, int vnf) {
        return nfvRoutingService.setONRouteMode(mode, vnf);
    }

    /**
     * Get Topology defined in OpenNaaS
     *
     * @return status
     * @throws org.opennaas.gui.nfvrouting.services.rest.RestServiceException
     */
    public Topology getTopology(int vnf) throws RestServiceException {
        return nfvRoutingService.getTopology(vnf);
    }

    public String setGenNetResource(String genNetResName, int vnf) {
        return nfvRoutingService.setGenNetResource(genNetResName, vnf);
    }
}
