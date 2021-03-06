package org.opennaas.extensions.network.helpers;

/*
 * #%L
 * OpenNaaS :: Network :: Model
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opennaas.core.resources.descriptor.CapabilityDescriptor;
import org.opennaas.core.resources.descriptor.CapabilityProperty;
import org.opennaas.core.resources.descriptor.Information;
import org.opennaas.core.resources.descriptor.ResourceDescriptor;
import org.opennaas.extensions.network.mock.MockNetworkDescriptor;

public class NetworkResourceDescriptorSupport {

	/**
	 * Create a resource with properties
	 * 
	 * @return
	 */
	public static ResourceDescriptor createNetworkDescriptor() {
		ResourceDescriptor config = new ResourceDescriptor();
		config.setId(new String("1"));
		/* resource description */
		Information info = new Information();
		info.setDescription("network description");
		info.setType("network");
		info.setName("networklayer1.0");
		info.setVersion("1.0.0");
		config.setInformation(info);

		/* capability description */
		CapabilityDescriptor capabilityDescriptor = new CapabilityDescriptor();
		List<CapabilityProperty> properties = new ArrayList<CapabilityProperty>();
		properties.add(new CapabilityProperty("name", "value"));
		capabilityDescriptor.setCapabilityProperties(properties);
		List<CapabilityDescriptor> capabilityDescriptors = new ArrayList<CapabilityDescriptor>();
		capabilityDescriptors.add(capabilityDescriptor);
		config.setCapabilityDescriptors(capabilityDescriptors);

		/* read from a file descriptor */
		config.setFileTopology("network/network_example1.xml");

		// Testing if it loads a network topology
		config.setNetworkTopology(MockNetworkDescriptor.newSimpleNDLNetworkDescriptor());

		Map<String, String> resourceReferences = new HashMap<String, String>();
		resourceReferences.put("router:test1", "router/XXXYYYZZZtest1-ID");
		resourceReferences.put("router:test2", "router/XXXYYYZZZtest2-ID");
		resourceReferences.put("router:test3", "router/XXXYYYZZZtest3-ID");
		config.setResourceReferences(resourceReferences);

		return config;
	}

	/**
	 * Create a resource with properties
	 * 
	 * @return
	 */
	public static ResourceDescriptor createNetworkDescriptorWithNetworkDomains() {
		ResourceDescriptor config = new ResourceDescriptor();
		config.setId(new String("1"));

		/* resource description */
		Information info = new Information();
		info.setDescription("network description");
		info.setType("network");
		info.setName("networklayer1.0");
		info.setVersion("1.0.0");
		config.setInformation(info);

		/* capability description */
		CapabilityDescriptor capabilityDescriptor = new CapabilityDescriptor();
		List<CapabilityProperty> properties = new ArrayList<CapabilityProperty>();
		properties.add(new CapabilityProperty("name", "value"));
		capabilityDescriptor.setCapabilityProperties(properties);
		List<CapabilityDescriptor> capabilityDescriptors = new ArrayList<CapabilityDescriptor>();
		capabilityDescriptors.add(capabilityDescriptor);
		config.setCapabilityDescriptors(capabilityDescriptors);

		/* read from a file descriptor */
		config.setFileTopology("network/network_diffs_layer.xml");

		// Testing if it loads a network topology
		config.setNetworkTopology(MockNetworkDescriptor.newNetworkDescriptorWithNetworkDomain());

		return config;
	}

	/**
	 * Create network descriptor
	 * 
	 * @return
	 */
	public static ResourceDescriptor createVirtualResourceDescriptor() {
		ResourceDescriptor config = new ResourceDescriptor();
		config.setId(new String("1"));
		Information info = new Information();
		info.setDescription("virtual resource description");
		info.setType("router");
		info.setName("logical1");
		info.setVersion("1.0.0");
		config.setInformation(info);

		CapabilityDescriptor capabilityDescriptor = new CapabilityDescriptor();
		List<CapabilityProperty> properties = new ArrayList<CapabilityProperty>();
		properties.add(new CapabilityProperty("name", "value"));
		capabilityDescriptor.setCapabilityProperties(properties);
		List<CapabilityDescriptor> capabilityDescriptors = new ArrayList<CapabilityDescriptor>();
		capabilityDescriptors.add(capabilityDescriptor);
		config.setCapabilityDescriptors(capabilityDescriptors);

		Map<String, String> props = new HashMap<String, String>();
		props.put("virtual", "true");
		config.setProperties(props);

		return config;
	}

}
