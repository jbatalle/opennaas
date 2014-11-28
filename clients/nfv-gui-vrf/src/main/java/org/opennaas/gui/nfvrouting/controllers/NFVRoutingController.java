package org.opennaas.gui.nfvrouting.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.opennaas.gui.nfvrouting.beans.insertRoutes;
import org.opennaas.gui.nfvrouting.bos.NFVRoutingBO;
import org.opennaas.gui.nfvrouting.bos.VNFManagementBO;
import org.opennaas.gui.nfvrouting.entities.route.Route;
import org.opennaas.gui.nfvrouting.entities.settings.Settings;
import org.opennaas.gui.nfvrouting.services.rest.RestServiceException;
import org.opennaas.gui.nfvrouting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Josep Batallé (josep.batalle@i2cat.net)
 */
@Controller
@RequestMapping("/secure/nfvRouting")
@SessionAttributes("settings")
public class NFVRoutingController {

    private static final Logger LOGGER = Logger.getLogger(NFVRoutingController.class);
    @Autowired
    protected NFVRoutingBO nfvRoutingBO;
    @Autowired
    protected VNFManagementBO vnfManagementBO;
    @Autowired
    protected ReloadableResourceBundleMessageSource messageSource;
    
    /**
     * Redirect to Configure view. Get the Route table of the given IP type.
     *
     * @param type
     * @param model
     * @param locale
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getRouteTable")
    public String getRouteTable(@RequestParam("type") String type, ModelMap model, Locale locale, HttpSession session) {
        LOGGER.error("Get Route Table ------------------> IPv" + type);
        Settings settings = null;
        if ((Settings) session.getAttribute("settings") != null) {
            model.put("settings", (Settings) session.getAttribute("settings"));
            settings = (Settings) session.getAttribute("settings");
        } else {
            model.addAttribute("errorMsg", "Session time out. Return to <a href='"+Constants.HOME_URL+"'>Home</a>");
        }
        if (settings == null) {
            settings = new Settings();
        }
        model.addAttribute("settings", settings);

        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }

        try {
            String response = nfvRoutingBO.getRouteTable(Integer.parseInt(type.split("IPv")[1]));
            if (response.equals("OpenNaaS is not started")) {
                model.addAttribute("errorMsg", response);
            }
            LOGGER.info("received json: " + response);
            model.addAttribute("json", response);
        } catch(NumberFormatException e){//handle the split type IP version
            model.addAttribute("errorMsg", "This type of table does not exist. Err: "+e.getMessage());
            model.addAttribute("json", "'null'");
            return "configRoute";
        } catch (RestServiceException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("json", "null");
            return "configRoute";
        }

        return "configRoute";
    }

    /**
     * **
     * Redirect to insert view
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/insertRoute")
    public String insertRoute(ModelMap model, HttpSession session) {
        if ((Settings) session.getAttribute("settings") != null) {
            model.put("settings", (Settings) session.getAttribute("settings"));
        } else {
            model.addAttribute("errorMsg", "Session time out. Return to <a href='"+Constants.HOME_URL+"'>Home</a>");
        }
        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }

        model.addAttribute(new insertRoutes());
        return "insertRoute";
    }

    /**
     * Redirect to insert view and insert the values received by POST. 
     * This function is used when we insert routes using the FORM after press Update
     *
     * @param route
     * @param result
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/insertRoute")
    public String insertRoutePost(insertRoutes route, BindingResult result, ModelMap model, HttpSession session) {
        LOGGER.info("Insert route ------------------> " + route.getListRoutes());
        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }
        try {
            for (Route r : route.getListRoutes()) {
                String response = nfvRoutingBO.insertRoute(r);
                String response2 = vnfManagementBO.copyRoutesToOtherVNF(1);
                model.addAttribute("json", response);
            }
            model.addAttribute("infoMsg", "Route addded correctly.");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }

        return "insertRoute";
    }

    /**
     * Scenario view. Show the topology and allow to open a terminal of each host using ShellinaBox
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/demonstrator")
    public String demonstrator(ModelMap model, HttpSession session) {
        LOGGER.info("Demonstator ------------------> ");
        Settings settings = null;
        if ((Settings) session.getAttribute("settings") != null) {
            model.put("settings", (Settings) session.getAttribute("settings"));
        } else {
            model.addAttribute("errorMsg", "Session time out. Return to <a href='"+Constants.HOME_URL+"'>Home</a>");
        }
        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }

        if (settings == null) {
            settings = new Settings();
        }
        model.addAttribute("settings", settings);

        return "demonstrator";
    }
    
    /**
     * Scenario view. Show the topology and allow to open a terminal of each host using ShellinaBox
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/vnfmgt")
    public String vnfMgt(ModelMap model, HttpSession session) {
        LOGGER.info("VNF Management ------------------> ");
        Settings settings = null;
        if ((Settings) session.getAttribute("settings") != null) {
            model.put("settings", (Settings) session.getAttribute("settings"));
        } else {
            model.addAttribute("errorMsg", "Session time out. Return to <a href='"+Constants.HOME_URL+"'>Home</a>");
        }
        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }

        if (settings == null) {
            settings = new Settings();
        }
        model.addAttribute("settings", settings);

        return "vnfmgt";
    }
    
    /**
     * Scenario view. Show the topology and allow to open a terminal of each host using ShellinaBox
     *
     * @param name
     * @param CtrlIP
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/vnfmgt/{name}/{CtrlIP}")
    public String vnfMgtAction(@PathVariable("name") String name, @PathVariable("CtrlIP") String CtrlIP, ModelMap model, HttpSession session) {
        LOGGER.info("VNF Management Action ------------------> ");
        Settings settings = null;
        
        String response = vnfManagementBO.duplicateVNF(name, CtrlIP);
                
        if ((Settings) session.getAttribute("settings") != null) {
            model.put("settings", (Settings) session.getAttribute("settings"));
        } else {
            model.addAttribute("errorMsg", "Session time out. Return to <a href='"+Constants.HOME_URL+"'>Home</a>");
        }
        if ((String) session.getAttribute("topologyName") != null) {
            model.put("topologyName", (String) session.getAttribute("topologyName"));
        }

        if (settings == null) {
            settings = new Settings();
        }
        model.addAttribute("settings", settings);

        return "vnfmgt";
    }
 
}
