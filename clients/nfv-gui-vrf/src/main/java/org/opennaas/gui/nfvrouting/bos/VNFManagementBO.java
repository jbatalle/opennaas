package org.opennaas.gui.nfvrouting.bos;

import org.opennaas.gui.nfvrouting.services.rest.routing.VNFService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Josep
 */
public class VNFManagementBO {

    @Autowired
    private VNFService vnfService;

    public String duplicateVNF(String name, String ip) {
        return vnfService.duplicateVNF(name, ip);
    }
}
