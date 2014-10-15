package org.opennaas.extensions.vrf.dijkstraroute.capability;

/*
 * #%L
 * OpenNaaS :: Virtual Routing Function :: Dijkstra Route
 * %%
 * Copyright (C) 2007 - 2014 Fundació Privada i2CAT, Internet i Innovació a Catalunya
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opennaas.extensions.genericnetwork.model.driver.DevicePortId;
import org.opennaas.extensions.genericnetwork.model.topology.Host;
import org.opennaas.extensions.genericnetwork.model.topology.Link;
import org.opennaas.extensions.genericnetwork.model.topology.NetworkElement;
import org.opennaas.extensions.genericnetwork.model.topology.Port;
import org.opennaas.extensions.genericnetwork.model.topology.Switch;
import org.opennaas.extensions.genericnetwork.model.topology.Topology;
import org.opennaas.extensions.vrf.model.topology.TopologyInfo;
import org.opennaas.extensions.vrf.utils.Utils;
import org.opennaas.extensions.vrf.utils.UtilsTopology;

/**
 *
 * @author josep
 */
public class RoutingCapabilityTest {

    public RoutingCapabilityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDynamicRoute method, of class RoutingCapability.
     */
    @Test
    public void testGetDynamicRoute() {
        System.out.println("getDynamicRoute");
        String source = String.valueOf(Utils.StringIPv4toInt("192.168.2.1"));
        String target = String.valueOf(Utils.StringIPv4toInt("192.168.2.2"));
        String DPID = "";
        int inPort = 0;
        DijkstraRoutingCapability instance = new DijkstraRoutingCapability();
        instance.setTopologyInfo(mockGetTopology(1));
        Response expResult = null;
        Response result = instance.getDynamicRoute(source, target, DPID, inPort);
        assertEquals(404, result.getStatus());
        // TODO review the generated test code and remove the default call to fail.
 
        source = String.valueOf(Utils.StringIPv4toInt("10.0.1.1"));
        target = String.valueOf(Utils.StringIPv4toInt("10.0.2.2"));
        DPID = "00:00:00:00:00:00:00:01";
        inPort = 1;
        instance = new DijkstraRoutingCapability();
        instance.setTopologyInfo(mockGetTopology(1));
        result = instance.getDynamicRoute(source, target, DPID, inPort);
        assertEquals(200, result.getStatus());
        
    }

    private TopologyInfo mockGetTopology(int staticDijkstraCost) {
        Topology topo;

        Port p1 = new Port();
        p1.setId("s1.1");
        Port p2 = new Port();
        p2.setId("s1.2");
        Port p3 = new Port();
        p3.setId("s2.1");
        Port p4 = new Port();
        p4.setId("s2.2");
        Port ph1 = new Port();
        ph1.setId("h1.1");
        Port ph2 = new Port();
        ph2.setId("h2.1");

        Set<Port> h1ports = new HashSet<Port>();
        h1ports.add(ph1);
        Set<Port> h2ports = new HashSet<Port>();
        h2ports.add(ph2);
        Set<Port> s1ports = new HashSet<Port>();
	s1ports.add(p1);
        s1ports.add(p2);
        Set<Port> s2ports = new HashSet<Port>();
        s2ports.add(p3);
        s2ports.add(p4);

        Host h1 = new Host();
        h1.setId("host:h1");
        h1.setIp("10.0.1.1");
        h1.setPorts(h1ports);
        Host h2 = new Host();
        h2.setId("host:h2");
        h2.setIp("10.0.2.2");
        h2.setPorts(h2ports);
        Switch s1 = new Switch();
        s1.setId("openflowswitch:s1");
        s1.setPorts(s1ports);
        s1.setDpid("00:00:00:00:00:00:00:01");
        Switch s2 = new Switch();
        s2.setId("openflowswitch:s2");
        s2.setPorts(s2ports);
        s2.setDpid("00:00:00:00:00:00:00:02");

        Set<NetworkElement> networkElements = new HashSet<NetworkElement>();
        networkElements.add(s1);
        networkElements.add(s2);
        networkElements.add(h1);
        networkElements.add(h2);

        Link link1 = new Link();
        link1.setSrcPort(ph1);
        link1.setDstPort(p1);
        Link link2 = new Link();
        link2.setSrcPort(p2);
        link2.setDstPort(p3);
        Link link3 = new Link();
        link3.setSrcPort(p4);
        link3.setDstPort(ph2);

        Set<Link> links = new HashSet<Link>();
        links.add(link1);
        links.add(link2);
        links.add(link3);

        DevicePortId devicePortIdSwitch1 = new DevicePortId();
        devicePortIdSwitch1.setDeviceId("openflowswitch:s1");
        devicePortIdSwitch1.setDevicePortId("1");
        DevicePortId devicePortIdSwitch2 = new DevicePortId();
        devicePortIdSwitch2.setDeviceId("openflowswitch:s1");
        devicePortIdSwitch2.setDevicePortId("2");
        DevicePortId devicePortIdSwitch3 = new DevicePortId();
        devicePortIdSwitch3.setDeviceId("openflowswitch:s2");
        devicePortIdSwitch3.setDevicePortId("1");
        DevicePortId devicePortIdSwitch4 = new DevicePortId();
        devicePortIdSwitch4.setDeviceId("openflowswitch:s2");
        devicePortIdSwitch4.setDevicePortId("2");
        DevicePortId devicePortIdHost1 = new DevicePortId();
        devicePortIdHost1.setDeviceId("host:h1");
        devicePortIdHost1.setDevicePortId("1");
        DevicePortId devicePortIdHost2 = new DevicePortId();
        devicePortIdHost2.setDeviceId("host:h2");
        devicePortIdHost2.setDevicePortId("1");

        BiMap<String, DevicePortId> portMap = HashBiMap.create();
        portMap.put(p1.getId(), devicePortIdSwitch1);
        portMap.put(p2.getId(), devicePortIdSwitch2);
        portMap.put(p3.getId(), devicePortIdSwitch3);
        portMap.put(p4.getId(), devicePortIdSwitch4);
        portMap.put(ph1.getId(), devicePortIdHost1);
        portMap.put(ph2.getId(), devicePortIdHost2);

        topo = new Topology();
        topo.setLinks(links);
        topo.setNetworkElements(networkElements);
        topo.setNetworkDevicePortIdsMap(portMap);

        return UtilsTopology.createAdjacencyMatrix(topo, staticDijkstraCost);
    }
}
