package net.i2cat.mantychore.action.junos.test;

import java.io.IOException;

import junit.framework.Assert;
import net.i2cat.mantychore.actionsets.junos.ActionConstants;
import net.i2cat.mantychore.actionsets.junos.actions.gretunnel.CreateTunnelAction;
import net.i2cat.mantychore.actionsets.junos.actions.test.ActionTestHelper;
import net.i2cat.mantychore.model.ComputerSystem;
import net.i2cat.mantychore.model.GRETunnelConfiguration;
import net.i2cat.mantychore.model.GRETunnelEndpoint;
import net.i2cat.mantychore.model.GRETunnelService;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opennaas.core.protocols.sessionmanager.impl.ProtocolSessionManager;
import org.opennaas.core.resources.action.ActionException;
import org.opennaas.core.resources.action.ActionResponse;

public class CreateTunnelActionTest {
	private static CreateTunnelAction		action;
	private static ActionTestHelper			helper;
	private static ProtocolSessionManager	protocolsessionmanager;

	@BeforeClass
	public static void init() {
		action = new CreateTunnelAction();
		action.setModelToUpdate(new ComputerSystem());
		helper = new ActionTestHelper();
		action.setParams(helper.newParamsInterfaceEthernet());
		helper = new ActionTestHelper();
		protocolsessionmanager = helper.getProtocolSessionManager();
	}

	@Test
	public void actionIDTest() {
		Assert.assertEquals("Wrong ActionID", ActionConstants.CREATETUNNEL,
				action.getActionID());
	}

	@Test
	public void paramsTest() {
		// this action always have null params
		Assert.assertNotNull("Null parameters", action.getParams());
	}

	@Test
	public void templateTest() {
		// this action always have this template as a default
		Assert.assertEquals("Not accepted param", "/VM_files/createTunnel.vm", action.getTemplate());
	}

	/**
	 * Execute the action
	 * 
	 * @throws IOException
	 */
	@Test
	public void executeActionTest() throws IOException {
		action.setModelToUpdate(new ComputerSystem());

		// Add params
		GRETunnelService greTunnelService = getGRETunnelService();
		action.setParams(greTunnelService);

		try {
			ActionResponse response = action.execute(protocolsessionmanager);
			Assert.assertTrue(response.getActionID()
					.equals(ActionConstants.CREATETUNNEL));
		} catch (ActionException e) {
			e.printStackTrace();
			Assert.fail();
		}

		net.i2cat.mantychore.model.System computerSystem = (net.i2cat.mantychore.model.System) action.getModelToUpdate();
		Assert.assertNotNull(computerSystem);
	}

	/**
	 * Get the GRETunnelService
	 * 
	 * @return GRETunnelService
	 * @throws IOException
	 */
	private GRETunnelService getGRETunnelService() {

		GRETunnelService greService = new GRETunnelService();
		greService.setElementName("");
		greService.setName("gre.1");

		GRETunnelConfiguration greConfig = new GRETunnelConfiguration();
		greConfig.setSourceAddress("147.56.89.62");
		greConfig.setDestinationAddress("193.45.23.1");

		GRETunnelEndpoint gE = new GRETunnelEndpoint();
		gE.setIPv4Address("192.168.32.1");
		gE.setSubnetMask("255.255.255.0");

		greService.setGRETunnelConfiguration(greConfig);
		greService.addProtocolEndpoint(gE);

		return greService;
	}

}