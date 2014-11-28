package org.opennaas.gui.nfvrouting.bos;

import org.opennaas.gui.nfvrouting.services.rest.routing.VNFService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Josep
 */
public class VNFManagementBO {

    @Autowired
    private VNFService vnfService;

    public String duplicateVNF(String name, String CtrlIP) {
        return vnfService.duplicateVNF(name, CtrlIP);
    }

    public String copyRoutesToOtherVNF(int i) {
         return vnfService.copyRoutesToOtherVNF(i);
    }
}
