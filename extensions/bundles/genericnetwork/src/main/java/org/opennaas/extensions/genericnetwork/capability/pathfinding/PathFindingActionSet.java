package org.opennaas.extensions.genericnetwork.capability.pathfinding;

/*
 * #%L
 * OpenNaaS :: OF Network
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

import org.opennaas.core.resources.action.IActionSetDefinition;
import org.opennaas.extensions.genericnetwork.model.circuit.request.CircuitRequest;

/**
 * 
 * @author Adrian Rosello Rey (i2CAT)
 * 
 */
public class PathFindingActionSet implements IActionSetDefinition {

	/**
	 * An action that retrieves a route between the {@link Source} and the {@link Destination} of the {@link CircuitRequest}, according to the QoS
	 * parameters in it.
	 */
	public static final String	FIND_PATH_FOR_REQUEST	= "findPathForRequest";
}