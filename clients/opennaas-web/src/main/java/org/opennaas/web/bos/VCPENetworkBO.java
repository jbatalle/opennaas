/**
 * 
 */
package org.opennaas.web.bos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opennaas.core.resources.descriptor.CapabilityDescriptor;
import org.opennaas.core.resources.descriptor.Information;
import org.opennaas.core.resources.descriptor.vcpe.VCPENetworkDescriptor;
import org.opennaas.extensions.vcpe.model.Router;
import org.opennaas.extensions.vcpe.model.VCPENetworkElement;
import org.opennaas.extensions.vcpe.model.VCPENetworkModel;
import org.opennaas.extensions.vcpe.model.VCPETemplate;
import org.opennaas.extensions.vcpe.model.helper.VCPENetworkModelHelper;
import org.opennaas.web.entities.Interface;
import org.opennaas.web.entities.LogicalRouter;
import org.opennaas.web.entities.VCPENetwork;
import org.opennaas.web.services.ResourceService;
import org.opennaas.web.services.RestServiceException;
import org.opennaas.web.services.VCPENetworkService;
import org.opennaas.web.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jordi
 */
public class VCPENetworkBO {

	private static final Logger	LOGGER	= Logger.getLogger(VCPENetworkBO.class);

	@Autowired
	private ResourceService		resourceService;

	@Autowired
	private VCPENetworkService	vcpeNetworkService;

	/**
	 * Create a VCPE Network, start the resource and build the enviroment
	 * 
	 * @param vcpeNetwork
	 * @throws RestServiceException
	 */
	public String create(VCPENetwork vcpeNetwork) throws RestServiceException {
		LOGGER.debug("create a VCPENetwork: " + vcpeNetwork);
		String vcpeNetworkId = resourceService.createResource(getResourceDescriptor(vcpeNetwork));
		LOGGER.debug("start the VCPENetwork with id: " + vcpeNetworkId);
		resourceService.startResource(vcpeNetworkId);
		LOGGER.debug("create the VCPENetwork enviroment");
		vcpeNetworkService.createVCPENetwork(getVCPENetworkOpennaas(vcpeNetworkId, vcpeNetwork));
		return vcpeNetworkId;
	}

	/**
	 * Delete a VCPE Network. First stop the resource
	 * 
	 * @param vcpeNetworkId
	 * @throws RestServiceException
	 */
	public void delete(String vcpeNetworkId) throws RestServiceException {
		LOGGER.debug("destroy the VCPENetwork enviroment");
		vcpeNetworkService.deleteVCPENetwork(vcpeNetworkId);
		LOGGER.debug("stop a VCPENetwork with id: " + vcpeNetworkId);
		resourceService.stopResource(vcpeNetworkId);
		LOGGER.debug("delete a VCPENetwork with id: " + vcpeNetworkId);
		resourceService.deleteResource(vcpeNetworkId);

	}

	/**
	 * Get a VCPE Network with id = vcpeNetworkId
	 * 
	 * @param vcpeNetworkId
	 * @return VCPENetwork
	 * @throws RestServiceException
	 */
	public VCPENetwork getById(String vcpeNetworkId) throws RestServiceException {
		LOGGER.debug("get a VCPENetwork with id: " + vcpeNetworkId);
		VCPENetworkModel openNaasModel = vcpeNetworkService.getVCPENetworkById(vcpeNetworkId);
		return getVCPENetworkGUI(openNaasModel);
	}

	/**
	 * Get all VCPE Network
	 * 
	 * @return List<VCPENetwork>
	 * @throws RestServiceException
	 */
	public List<VCPENetwork> getAllVCPENetworks() throws RestServiceException {
		LOGGER.debug("get all VCPENetwork");
		return getListVCPENetworkGUI(vcpeNetworkService.getAllVCPENetworks());
	}

	/**
	 * Get the descriptor with his capability to create the resource
	 * 
	 * @param params
	 * @return VCPENetworkDescriptor
	 */
	private VCPENetworkDescriptor getResourceDescriptor(VCPENetwork vcpeNetwork) {
		VCPENetworkDescriptor descriptor = new VCPENetworkDescriptor();
		Information information = new Information();
		information.setType(Constants.RESOURCE_VCPENET_TYPE);
		information.setName(vcpeNetwork.getName());
		descriptor.setInformation(information);
		// Capability
		List<CapabilityDescriptor> capabs = new ArrayList<CapabilityDescriptor>();
		capabs.add(getBuilderCapability());
		descriptor.setCapabilityDescriptors(capabs);
		return descriptor;
	}

	/**
	 * Get the builder capability of the VCPENetwork to create the resource
	 * 
	 * @return CapabilityDescriptor
	 */
	private CapabilityDescriptor getBuilderCapability() {
		CapabilityDescriptor desc = new CapabilityDescriptor();
		Information info = new Information();
		info.setType(Constants.CAPABILITY_VCPENET_BUILDER);
		desc.setCapabilityInformation(info);
		return desc;
	}

	/**
	 * @param allVCPENetworks
	 * @return
	 */
	private List<VCPENetwork> getListVCPENetworkGUI(List<VCPENetworkModel> listModelIn) {
		List<VCPENetwork> listModelOut = new ArrayList<VCPENetwork>();
		for (int i = 0; i < listModelIn.size(); i++) {
			listModelOut.add(getVCPENetworkGUI(listModelIn.get(i)));
		}
		return listModelOut;
	}

	/**
	 * Convert a OpenNaaS model to a GUI VCPENetwork model
	 * 
	 * @param openNaasModel
	 * @return VCPENetwork
	 */
	private VCPENetwork getVCPENetworkGUI(VCPENetworkModel modelIn) {
		VCPENetwork modelOut = new VCPENetwork();
		modelOut.setId(modelIn.getVcpeNetworkId());
		modelOut.setName(modelIn.getVcpeNetworkName());

		Router logicalRouter1 = (Router) VCPENetworkModelHelper
				.getElementByNameInTemplate(modelIn, VCPETemplate.VCPE1_ROUTER);
		Router logicalRouter2 = (Router) VCPENetworkModelHelper
				.getElementByNameInTemplate(modelIn, VCPETemplate.VCPE1_ROUTER);

		modelOut.setLogicalRouter1(getLRGUI(logicalRouter1));
		modelOut.setLogicalRouter1(getLRGUI(logicalRouter2));
		return modelOut;
	}

	/**
	 * Return a GUI logical router from a OpenNaaS logical router
	 * 
	 * @param inLR
	 * @return
	 */
	private LogicalRouter getLRGUI(Router lrIn) {
		LogicalRouter lrOut = new LogicalRouter();
		lrOut.setName(lrIn.getName());
		// Interfaces
		List<org.opennaas.web.entities.Interface> interfaces = new ArrayList<org.opennaas.web.entities.Interface>();
		lrOut.setInterfaces(interfaces);
		// Interface Inter
		interfaces.add(getInterfaceGUI(lrIn.getInterfaces().get(0)));
		// Interface Down
		interfaces.add(getInterfaceGUI(lrIn.getInterfaces().get(1)));
		// Interface Up
		interfaces.add(getInterfaceGUI(lrIn.getInterfaces().get(2)));
		return lrOut;
	}

	/**
	 * @param string
	 * @param interface1
	 * @return
	 */
	private Interface getInterfaceGUI(org.opennaas.extensions.vcpe.model.Interface interfaceIn) {
		org.opennaas.web.entities.Interface outIface = new org.opennaas.web.entities.Interface();
		outIface.setName(org.opennaas.web.entities.Interface.getNameFromCompleteName(interfaceIn.getName()));
		outIface.setPort(org.opennaas.web.entities.Interface.getPortFromCompleteName(interfaceIn.getName()));
		outIface.setIpAddress(interfaceIn.getIpAddress());
		outIface.setVlan((int) interfaceIn.getVlanId());
		return outIface;
	}

	/**
	 * Get params to call the ws to create the VCPENetwork enviroment
	 * 
	 * @param vcpeNetworkId
	 * 
	 * @param vcpeNetwork
	 * @return CreateVCPENetRequest
	 */
	private VCPENetworkModel getVCPENetworkOpennaas(String vcpeNetworkName, VCPENetwork vcpeNetwork) {
		VCPENetworkModel request = new VCPENetworkModel();
		// Id
		request.setVcpeNetworkName(vcpeNetworkName);
		// Elements
		List<VCPENetworkElement> elements = new ArrayList<VCPENetworkElement>();
		request.setElements(elements);
		// LogicalRouters
		org.opennaas.extensions.vcpe.model.LogicalRouter logicalRouter1 = getLROpennaas(vcpeNetwork.getLogicalRouter1());
		org.opennaas.extensions.vcpe.model.LogicalRouter logicalRouter2 = getLROpennaas(vcpeNetwork.getLogicalRouter2());
		elements.add(logicalRouter1);
		elements.add(logicalRouter2);
		// Add interfaces to elements
		elements.addAll(logicalRouter1.getInterfaces());
		elements.addAll(logicalRouter2.getInterfaces());
		return request;
	}

	/**
	 * Return a OpenNaaS logical router from a GUI logical router
	 * 
	 * @param string
	 * 
	 * @param logicalRouter1
	 * @return
	 */
	private org.opennaas.extensions.vcpe.model.LogicalRouter getLROpennaas(LogicalRouter lrIn) {
		org.opennaas.extensions.vcpe.model.LogicalRouter lrOut = new org.opennaas.extensions.vcpe.model.LogicalRouter();
		lrOut.setName(lrIn.getName());
		lrOut.setNameInTemplate(lrIn.getName());
		// Interfaces
		List<org.opennaas.extensions.vcpe.model.Interface> interfaces = new ArrayList<org.opennaas.extensions.vcpe.model.Interface>();
		lrOut.setInterfaces(interfaces);
		// Interface Inter
		interfaces.add(getInterfaceOpennaas(lrIn.getName() + "-inter", lrIn.getInterfaces().get(0)));
		// Interface Down
		interfaces.add(getInterfaceOpennaas(lrIn.getName() + "-down", lrIn.getInterfaces().get(1)));
		// Interface Up
		interfaces.add(getInterfaceOpennaas(lrIn.getName() + "-up", lrIn.getInterfaces().get(2)));
		return lrOut;
	}

	/**
	 * Return a OpenNaaS interface from a GUI interface
	 * 
	 * @param interface1
	 * @return Interface
	 */
	private org.opennaas.extensions.vcpe.model.Interface getInterfaceOpennaas(String name, Interface inIface) {
		org.opennaas.extensions.vcpe.model.Interface outIface = new org.opennaas.extensions.vcpe.model.Interface();
		outIface.setName(inIface.getCompleteName());
		outIface.setIpAddress(inIface.getIpAddress());
		outIface.setVlanId(inIface.getVlan());
		outIface.setNameInTemplate(name);
		return outIface;
	}
}