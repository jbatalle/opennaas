package org.opennaas.gui.ofertie.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.opennaas.extensions.genericnetwork.capability.nclprovisioner.api.CircuitCollection;
import org.opennaas.extensions.genericnetwork.model.circuit.Circuit;
import org.opennaas.extensions.genericnetwork.model.circuit.NetworkConnection;
import org.opennaas.extensions.genericnetwork.model.circuit.Route;
import org.opennaas.extensions.genericnetwork.model.driver.DevicePortId;
import org.opennaas.extensions.genericnetwork.model.portstatistics.TimePeriod;
import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.extensions.openflowswitch.capability.controllerinformation.model.MemoryUsage;
import org.opennaas.extensions.openflowswitch.capability.controllerinformation.model.HealthState;
import org.opennaas.gui.ofertie.bos.DolfinBO;
import org.opennaas.gui.ofertie.entities.GuiCircuits;
import org.opennaas.gui.ofertie.entities.GuiSwitch;
import org.opennaas.gui.ofertie.entities.GuiTopology;
import org.opennaas.gui.ofertie.entities.OfertieTopology;
import org.opennaas.gui.ofertie.services.rest.RestServiceException;
import org.opennaas.gui.ofertie.utils.model.DolfinBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
@Controller
@RequestMapping("/secure/ofertie/ajax")
public class AjaxController {
    private static final Logger LOGGER = Logger.getLogger(DolfinController.class);
    @Autowired
    protected DolfinBO dolfinBO;
    private OfertieTopology dolfinTopology;
    private CircuitCollection allocatedCircuits;

    /**
     * Request the Flow Table of switch.
     *
     * @param dpid
     * @return Flow table in xml representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/switchInfo/{dpid}")
    public @ResponseBody String getAllocatedFlowsbyDPID(@PathVariable("dpid") String dpid) {
        LOGGER.debug("Get allocated flows take into account the DPID: " + dpid);
        String response = "";
        try {
            response = dolfinBO.getAllocatedFlows(dpid);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Request the allocated flows
     *
     * @param switchName
     * @return the information of the switch (IP:port)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllocatedFlows/{switchName}")
    public @ResponseBody String getAllocatedFlowsbyName(@PathVariable("switchName") String switchName) {
        LOGGER.debug("Get allocated flows take into account the switch Name: "+switchName);
        String response = dolfinBO.getAllocatedFlows(switchName);
        return response;
    }

    /**
     * Get topology
     * @return a json file that contains the Topology definiton
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getTopology")
    public @ResponseBody Topology getTopology() {
        LOGGER.error("Get Topology");
        Topology response = null;
        try {
            response = dolfinBO.getTopology();
        } catch (RestServiceException ex) {
            java.util.logging.Logger.getLogger(AjaxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     * Obtain information of circuits due ajax.
     *
     * @return the Collection of Circuits
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getCircuits")
    public @ResponseBody CircuitCollection getAllocatedCircuits() {
        if (dolfinTopology == null) {
            try {
                dolfinTopology = DolfinBeanUtils.getTopology(dolfinBO.getTopology());
            } catch (RestServiceException ex) {
                java.util.logging.Logger.getLogger(DolfinController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            allocatedCircuits = dolfinBO.getAllocatedCircuits();
        } catch (RestServiceException ex) {
            generateCircuits();
            java.util.logging.Logger.getLogger(DolfinController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        GuiCircuitCollection guiCirColect = OfertieBeanUtils.mappingSwitchPort(allocatedCircuits, dolfinTopology);
        return allocatedCircuits;
    }

    /**
     * Return the Ofertie Circuit in XML format
     *
     * @param circuitId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getCircuit/{circuitId}")
    public @ResponseBody Circuit getAllocatedCircuit(@PathVariable("circuitId") String circuitId) {
        for (Circuit circuit : allocatedCircuits.getCircuits()) {
            if (circuit.getCircuitId().equals(circuitId)) {
                return circuit;
            }
        }
        return null;
    }

    /**
     * Return the Circuit used by the GUI in json format
     *
     * @param circuitId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getCircuitSwitches/{circuitId}")
    public @ResponseBody String getAllocatedCircuitSwitches(@PathVariable("circuitId") String circuitId) {
        GuiCircuits guiCircuits = null;
        String response;
LOGGER.error("CIRCUIT ID: "+circuitId);
LOGGER.error("CIRCUIT ID: "+dolfinTopology.getSwitches().get(0).getDpid());
        for (Circuit circuit : allocatedCircuits.getCircuits()) {
            if (circuit.getCircuitId().equals(circuitId)) {
                guiCircuits = DolfinBeanUtils.mappingSwitchPort(circuit, dolfinTopology);
            }
        }
        LOGGER.error("ListSwitches: ");
        LOGGER.error("Size: "+guiCircuits.getGuiSwitches().size());
        for(GuiSwitch c : guiCircuits.getGuiSwitches()){
            LOGGER.error(c.getName());
        }
        response = DolfinBeanUtils.mapperObjectsToJSON(guiCircuits);
        return response;
    }

    /**
     * Return the switch id given a port number
     *
     * @param port
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSwitchByPort/{port}")
    public @ResponseBody String getSwitchByPort(@PathVariable("port") String port) {
        return DolfinBeanUtils.getSwitchOfPort(port, dolfinTopology);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/topologyToGUI")
    public @ResponseBody  String topologyToGuiTopology(ModelMap model) {
        GuiTopology guiTop = null;
        Map<String, String> possibleHosts;
        try {
            guiTop = DolfinBeanUtils.convertONTopologyToGuiTopology(dolfinBO.getTopology());
            possibleHosts = DolfinBeanUtils.findUnusedPorts(dolfinBO.getTopology(), guiTop);
            guiTop.setPosibleHosts(possibleHosts);
        } catch (RestServiceException ex) {
            java.util.logging.Logger.getLogger(DolfinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String response = DolfinBeanUtils.mapperObjectsToJSON(guiTop);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public @ResponseBody String test() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            GuiTopology guiTop = DolfinBeanUtils.convertONTopologyToGuiTopology(dolfinBO.getTopology());
            map = DolfinBeanUtils.findUnusedPorts(dolfinBO.getTopology(), guiTop);
        } catch (RestServiceException ex) {
            java.util.logging.Logger.getLogger(DolfinController.class.getName()).log(Level.SEVERE, null, ex);
        }
       return DolfinBeanUtils.mapperObjectsToJSON(map);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/portIdsMap")
    public @ResponseBody Map<String, DevicePortId> portIdsMap() throws RestServiceException{
        Topology top = dolfinBO.getTopology();
        Map<String, DevicePortId> possibleHosts;
        possibleHosts = top.getNetworkDevicePortIdsMap();
        return possibleHosts;
/*        Iterator<String> keySetIterator = possibleHosts.keySet().iterator();
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            DevicePortId dvP = possibleHosts.get(key);
            if()
                return dvP.getDevicePortId();
        }

        return null;*/
    }

    /**
     * Request the statistics of port
     *
     * @return Port Statistics table in xml representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/portStatistics")
    public @ResponseBody String getPortStatistics() {
        LOGGER.debug("Get port statistics");
        String response = "";
        TimePeriod tP = new TimePeriod();
        tP.setInit(System.currentTimeMillis() - 86400);
        tP.setEnd(System.currentTimeMillis());
        try {
            response = dolfinBO.getPortStatistics(tP);
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * Request the statistics of port
     *
     * @param dpid
     * @return Statistics in xml representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/portStatistics/{switchId}")
    public @ResponseBody String getSwitchStatistics(@PathVariable("switchId") String dpid) {
        LOGGER.debug("Get port statistics: " + dpid);
        String response;
        TimePeriod tP = new TimePeriod();
        tP.setInit(System.currentTimeMillis() - 86400);
        tP.setEnd(System.currentTimeMillis());
        LOGGER.error("TimeMilis: "+System.currentTimeMillis());
        try {
            response = dolfinBO.getPortStatistics(tP, dpid);
        } catch (Exception e) {
            return generatePortStatistics();
        }

        return response;
    }

    /**
     * Request circuit statistics
     *
     * @return Cicuits statistics in CSV representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/circuitStatistics")
    public @ResponseBody String getCircuitStatistics() {
        LOGGER.error("Get circuit statistics");
        String response = "";
        TimePeriod tP = new TimePeriod();
        tP.setInit(System.currentTimeMillis() - 86400);
        tP.setEnd(System.currentTimeMillis());
        LOGGER.error("TimeMilis: "+System.currentTimeMillis());
        try {
            response = dolfinBO.getCircuitStatistics(tP);
        } catch (Exception e) {
            //return generateCircuitStatistics();
        }
        return response;
    }

    /**
     * Request memory usage
     *
     * @param switchId
     * @return Xml with memory usage
     */
    @RequestMapping(method = RequestMethod.GET, value = "/memoryUsage/{switchId}")
    public @ResponseBody MemoryUsage getControllerMemoryUsage(@PathVariable("switchId") String switchId) {
        LOGGER.debug("Get memory Usage");
        MemoryUsage response = new MemoryUsage();
        try {
            response = dolfinBO.getControllerMemoryUsage(switchId);
            LOGGER.debug(response);
            //response = generateMemoryStatus();
        } catch (Exception e) {
        //    return response;
        }
        return response;
    }

    /**
     * Request controller status
     *
     * @param switchId
     * @return Cicuits statistics in CSV representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/healthState/{switchId}")
    public @ResponseBody HealthState getHealthState(@PathVariable("switchId") String switchId) {
        LOGGER.error("Get health status");
        HealthState response = null;
        try {
//            response = dolfinBO.getHealthState(switchId);
            return dolfinBO.getHealthState(switchId);
        } catch (Exception e) {
            LOGGER.error("Exception "+e.getLocalizedMessage());
            LOGGER.error("Exception "+e.getMessage());
            //return response;
        }
        return response;
    }

    private String generateCircuitStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("10000000").append(",").append("1").append(",")
                .append("10").append(",").append("2").append(",")
                .append("0").append(",").append("0").append(",")
                .append("1234").append("\n");

        sb.append("20000000").append(",").append("2").append(",")
                .append("20").append(",").append("1").append(",")
                .append("4").append(",").append("5").append(",")
                .append("1235");
        return sb.toString();
	}

    private String generatePortStatistics(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<timedPortStatistics>");
        sb.append("<statistics>");
        sb.append("<timestamp>1</timestamp>");
        sb.append("<switchId>switch1</switchId>");
        sb.append("<portId>p1</portId>");
        sb.append("<throughput>10</throughput>");
        sb.append("<packetLoss>0</packetLoss>");
        sb.append("</statistics>");
        sb.append("<statistics>");
        sb.append("<timestamp>2</timestamp>");
        sb.append("<switchId>switch1</switchId>");
        sb.append("<portId>p2</portId>");
        sb.append("<throughput>10</throughput>");
        sb.append("<packetLoss>1</packetLoss>");
        sb.append("</statistics>");
        sb.append("<statistics>");
        sb.append("<timestamp>3</timestamp>");
        sb.append("<switchId>switch2</switchId>");
        sb.append("<portId>p3</portId>");
        sb.append("<throughput>5</throughput>");
        sb.append("<packetLoss>14</packetLoss>");
        sb.append("</statistics>");
        sb.append("</timedPortStatistics>");
        return sb.toString();
    }
    private CircuitCollection generateCircuits(){
        // allocatedCircuits = dolfinBO.getAllocatedCircuits();
            CircuitCollection cC = new CircuitCollection();
            Collection<Circuit> clC = new ArrayList<Circuit>();
            Circuit c = new Circuit();
            c.setCircuitId("1");
            Route r = new Route();
            r.setId("aaa");
            List<NetworkConnection> nC = new ArrayList<NetworkConnection>();
            NetworkConnection nE = new NetworkConnection();
            nE.setId("1");
            nE.setName("name");
            nC.add(nE);
            r.setNetworkConnections(nC);
            c.setRoute(r);
            clC.add(c);

            c = new Circuit();
            c.setCircuitId("1");
            r = new Route();
            r.setId("bbbb");
            nC = new ArrayList<NetworkConnection>();
            nE = new NetworkConnection();
            nE.setId("2");
            nE.setName("name2");
            nC.add(nE);
            r.setNetworkConnections(nC);
            c.setRoute(r);
            clC.add(c);
            cC.setCircuits(clC);
            return cC;
    }

    private MemoryUsage generateMemoryStatus(){
        MemoryUsage m = new MemoryUsage();
        long lfree = 1;
        long ltotal = 1;
        m.setFree(lfree);
        m.setTotal(ltotal);
        return m;
    }
    
    /**
     * Request insertPath
     *
     * @param switchId
     * @return Xml with memory usage
     */
    @RequestMapping(method = RequestMethod.GET, value = "/insertPath/{srcIp}/{srcIp}/{dstIp}/{label}/{minL}/{maxL}/{minJ}/{maxJ}/{minT}/{maxT}/{minPL}/{maxPL}")
    public @ResponseBody String insertPath(@PathVariable("srcIp") String srcIp, @PathVariable("dstIp") String dstIp, @PathVariable("label") String label, @PathVariable("minL") String minL, @PathVariable("maxL") String maxL, @PathVariable("minJ") String minJ, @PathVariable("maxJ") String maxJ, @PathVariable("minT") String minT, 
            @PathVariable("maxT") String maxT, @PathVariable("minPL") String minPL, @PathVariable("maxPL") String maxPL) {
        LOGGER.debug("Insert Path");
        
        String xml = setPathXml(srcIp, dstIp, label, minL, maxL, minJ, maxJ, minT, maxT, minPL, maxPL);
        String pathFinderUrl = "http://127.0.0.1:5000/pathfinder/provisioner";
        
        String response = "";
        try {
            response = dolfinBO.setPath(pathFinderUrl, xml);
            LOGGER.debug(response);
        } catch (Exception e) {
        //    return response;
        }
        return response;
    }
   
    public String setPathXml(String srcIp, String dstIp, String label, String minL, String maxL, String minJ,
            String maxJ, String minT, String maxT, String minPL, String maxPL){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<ns2:qos_policy_request xmlns:ns2=\"opennaas.api\">\n" +
"        <source>\n" +
"                <address>"+srcIp+"</address>\n" +
"                <!-- <transportPort>22</transportPort> -->\n" +
"                <!--<linkPort>2</linkPort>-->\n" +
"        </source>\n" +
"        <destination>\n" +
"                <address>"+dstIp+"</address>\n" +
"                <!-- <transportPort>22</transportPort> -->\n" +
"                <!--<linkPort>2</linkPort>-->\n" +
"\n" +
"        </destination>\n" +
"        <label>"+label+"</label>\n" +
"        <qos_policy>\n" +
"                <minLatency>"+minL+"</minLatency>\n" +
"                <maxLatency>"+maxL+"</maxLatency>\n" +
"                <minJitter>"+minJ+"</minJitter>\n" +
"                <maxJitter>"+maxJ+"</maxJitter>\n" +
"                <minThroughput>"+minT+"</minThroughput>\n" +
"                <maxThroughput>"+maxT+"</maxThroughput>\n" +
"                <minPacketLoss>"+minPL+"</minPacketLoss>\n" +
"                <maxPacketLoss>"+maxPL+"</maxPacketLoss>\n" +
"        </qos_policy>\n" +
"</ns2:qos_policy_request>";
        
        return xml;
    }
}
