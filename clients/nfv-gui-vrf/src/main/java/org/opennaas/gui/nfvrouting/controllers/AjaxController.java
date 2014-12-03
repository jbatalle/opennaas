package org.opennaas.gui.nfvrouting.controllers;

import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.gui.nfvrouting.bos.NFVRoutingBO;
import org.opennaas.gui.nfvrouting.services.rest.RestServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
@Controller
@RequestMapping("/secure/nfvRouting/ajax")
public class AjaxController {
    private static final Logger LOGGER = Logger.getLogger(AjaxController.class);
    @Autowired
    protected NFVRoutingBO nfvRoutingBO;
    
    /**
     * Get topology
     * @return a json file that contains the Topology definiton
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getTopology")
    public @ResponseBody Topology getTopology() {
        LOGGER.error("Get Topology");
        Topology response = null;
        try {
            response = nfvRoutingBO.getTopology(1);
        } catch (RestServiceException ex) {
            java.util.logging.Logger.getLogger(AjaxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    /**
     * Remove the Route without redirect
     *
     * @param type
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deleteRoute/{id}")
    public @ResponseBody String deleteRoute(@RequestParam("type") String type, @PathVariable("id") int id, ModelMap model) {
        LOGGER.debug("Remove Route ------------------> " + id + ", type: "+type);
        String response = "";
        try {
            response = nfvRoutingBO.deleteRoute(id, Integer.parseInt(type.split("IPv")[1]), 1);
            model.addAttribute("json", response);
            model.addAttribute("infoMsg", "Route removed correctly.");
            response = nfvRoutingBO.deleteRoute(id, Integer.parseInt(type.split("IPv")[1]), 2);
        } catch(NumberFormatException e){//handle the split type IP version
            model.addAttribute("errorMsg", "This type of table does not exist. Err: "+e.getMessage());
            return "configRoute";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        } 
        return response;
    }

    /**
     * Remove all Routes without redirect
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deleteAllRoutes")
    public @ResponseBody String deleteAllRoutes(ModelMap model) {
        LOGGER.debug("Remove All Route ------------------> ");
        String response = "";
        try {
            response = nfvRoutingBO.deleteAllRoutes(1);
            response = nfvRoutingBO.deleteAllRoutes(2);
            model.addAttribute("json", response);
            model.addAttribute("infoMsg", "Route removed correctly.");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return response;
    }

    /**
     * Obtain information of a switch. In which controller is connected and the
     * Flow table.
     *
     * @param resourceName
     * @param model
     * @return the information of the switch (IP:port)
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getFlowTable/{resourceName}")
    public @ResponseBody String getAllocatedFlows(@PathVariable("resourceName") String resourceName, ModelMap model) {
        String response = nfvRoutingBO.getFlowTable(resourceName, 1);
        return response;
    }

    /**
     * Call directly to StaticRoute REST API in order to request a route
     *
     * @param ipSrc
     * @param ipDst
     * @param dpid
     * @param inPort
     * @param model
     * @return the route
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getRoute/{ipSrc}/{ipDst}/{dpid}/{inPort}/{vnf}")
    public @ResponseBody String getRoute(@PathVariable("ipSrc") String ipSrc, @PathVariable("ipDst") String ipDst,
            @PathVariable("dpid") String dpid, @PathVariable("inPort") String inPort, @PathVariable("vnf") int vnf, ModelMap model) {
        LOGGER.debug("Requested route: " + ipSrc + " " + ipDst + " " + dpid + " " + inPort + "------------------");
        String response = nfvRoutingBO.getRoute(ipSrc, ipDst, dpid, inPort, vnf);
        LOGGER.debug("Response: " + response);
        if(response.equals("Route Not found.")){
            return response;
        }
        return response.split(":", 2)[1];
    }

    /**
     * Insert a route using AJAX. 
     * Used when we draw the routes in the picture
     *
     * @param ipSrc
     * @param ipDst
     * @param dpid
     * @param inPort
     * @param dstPort
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/insertRoute/{ipSrc}/{ipDst}/{dpid}/{inPort}/{outPort}")
    public @ResponseBody String insertRoute(@PathVariable("ipSrc") String ipSrc, @PathVariable("ipDst") String ipDst,
            @PathVariable("dpid") String dpid, @PathVariable("inPort") String inPort, @PathVariable("outPort") String dstPort, ModelMap model) {
        LOGGER.info("Insert route ------------------> ");
        String response = "";
        try {
            response = nfvRoutingBO.insertRoute(ipSrc, ipDst, dpid, inPort, dstPort, 1);
            model.addAttribute("json", response);
            model.addAttribute("infoMsg", "Route addded correctly.");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }

        return response;
    }

    /**
     * Get route paths given ipSrc and IpDest
     * Used when we request a link of specific route (from Ip source to Ip dest)
     *
     * @param ipSrc
     * @param ipDst
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/route/{ipSrc}/{ipDst:.+}/{vnf}")
    public @ResponseBody String getRoute(@PathVariable("ipSrc") String ipSrc, @PathVariable("ipDst") String ipDst, @PathVariable("vnf") int vnf, ModelMap model) {
        LOGGER.debug("Get route " + ipSrc + " " + ipDst);
        String response = "";
        try {
            response = nfvRoutingBO.getRoute(ipSrc, ipDst, vnf);
            LOGGER.error(response);
            model.addAttribute("infoMsg", "Route obtained correctly.");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return response;
    }

    /**
     * Return a json file that contains the routes.
     *
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/routeAll")
    public @ResponseBody String getRouteAll(@RequestParam("type") String type, ModelMap model) {
        LOGGER.info("Get Route Table ------------------> IPv" + type);
        String response = "";
        try {
            response = nfvRoutingBO.getRouteTable(Integer.parseInt(type.split("IPv")[1]), 1);
        } catch(NumberFormatException e){//handle the split type IP version
            model.addAttribute("errorMsg", "This type of table does not exist. Err: "+e.getMessage());
            return "configRoute";
        } catch (RestServiceException ex) {
            java.util.logging.Logger.getLogger(NFVRoutingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    /**
     * Return route mode (static/dynamic)
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getONRouteMode")
    public @ResponseBody String getONRouteMode(ModelMap model) {
        String response = "";
        try {
            response = nfvRoutingBO.getONRouteMode(1);
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return response;
    }

    /**
     * Set Route mode using AJAX
     *
     * @param mode
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/setONRouteMode/{mode}")
    public @ResponseBody String setONRouteMode(@PathVariable("mode") String mode, ModelMap model) {
        LOGGER.error("Set Route Mode ------------------> Mode" + mode);
        String response = "";
        try {
            response = nfvRoutingBO.setONRouteMode(mode, 1);
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return response;
    }

    /**
     * Request the Flow Table of switch.
     *
     * @param sName
     * @param model
     * @param locale
     * @param session
     * @return Flow table in xml representation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/switchInfo/{switchName}")
    public @ResponseBody String getFlowTable(@PathVariable("switchName") String sName, Model model, Locale locale, HttpSession session) {
        LOGGER.debug("Request switch information of switch with the following SwitchName: " + sName);
        String response = "";
        try {
            response = nfvRoutingBO.getFlowTable(sName, 1);
        } catch (Exception e) {
            return response;
        }
        return response;
    }
}
