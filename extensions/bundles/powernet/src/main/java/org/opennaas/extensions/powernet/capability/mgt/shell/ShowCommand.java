package org.opennaas.extensions.powernet.capability.mgt.shell;

/*
 * #%L
 * OpenNaaS :: Power Net Resource
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

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.opennaas.core.resources.IResource;
import org.opennaas.core.resources.shell.GenericKarafCommand;
import org.opennaas.extensions.powernet.model.PowerNetModel;

@Command(scope = "gim", name = "show", description = "Shows GIM model")
public class ShowCommand extends GenericKarafCommand {

	@Argument(index = 0, name = "resourceType:resourceName", description = "The resource id", required = true, multiValued = false)
	private String	resourceName;

	@Override
	protected Object doExecute() throws Exception {
		printInitCommand("showGIM");
		try {
			IResource resource = getResourceFromFriendlyName(resourceName);
			printPowerNetModel((PowerNetModel) resource.getModel());
		} catch (Exception e) {
			printError("Error in showGIM for resource " + resourceName);
			printError(e);
		} finally {
			printEndCommand();
		}
		printEndCommand();
		return null;
	}

	private void printPowerNetModel(PowerNetModel model) {
		printSymbol(model.toString());
	}

}
