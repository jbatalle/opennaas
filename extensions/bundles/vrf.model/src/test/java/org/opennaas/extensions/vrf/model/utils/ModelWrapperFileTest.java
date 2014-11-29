package org.opennaas.extensions.vrf.model.utils;

/*
 * #%L
 * OpenNaaS :: Virtual Routing Function :: Static Routing
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.opennaas.extensions.vrf.utils.Utils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.opennaas.core.resources.ActivatorException;
import org.opennaas.core.resources.IResource;
import org.opennaas.core.resources.IResourceIdentifier;
import org.opennaas.core.resources.IResourceManager;
import org.opennaas.core.resources.ResourceException;
import org.opennaas.extensions.openflowswitch.model.FloodlightOFAction;
import org.opennaas.extensions.openflowswitch.model.FloodlightOFFlow;
import org.opennaas.extensions.openflowswitch.model.FloodlightOFMatch;
import org.opennaas.extensions.openflowswitch.model.OFFlow;
import org.opennaas.extensions.genericnetwork.model.circuit.NetworkConnection;
import org.opennaas.extensions.genericnetwork.model.topology.Port;
//import org.opennaas.extensions.sdnnetwork.model.Port;
import org.opennaas.extensions.vrf.model.L2Forward;
import org.opennaas.extensions.vrf.model.VRFRoute;

/**
 *
 * @author josep
 */
public class ModelWrapperFileTest {

    private final static String PATH_FILE = "/routes/sampleJSONRoutes.json";
    private final static String FILE_NAME = "sampleJSONRoutes";
    private final static int version = 4;


    /**
     * Test of insertRouteFile method, of class RoutingCapability.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testInsertRouteFile() throws IOException {
        System.out.println("insertRouteFile");
        String filename = textFileToString(PATH_FILE);
        InputStream is = new ByteArrayInputStream(filename.getBytes("UTF-8"));
        String myString = IOUtils.toString(is, "UTF-8");
        System.out.println(myString);
        insertRoutesFromString(myString);
        assertEquals(200, 200);
    }

    private String textFileToString(String fileLocation) throws IOException {
        String fileString = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileLocation)));
        String line;
        while ((line = br.readLine()) != null) {
            fileString += line += "\n";
        }
        br.close();
        return fileString;
    }

    public static Response insertRoutesFromString(String content) {
        String response = "Inserted";
        List<VRFRoute> routes = new ArrayList<VRFRoute>();
        try {
            JsonFactory f = new MappingJsonFactory();
            JsonParser jp = f.createJsonParser(content);
            JsonToken current = jp.nextToken();
            if (current != JsonToken.START_OBJECT) {
                return Response.status(404).entity("Error: root should be object: quiting.").build();
            }
            while (jp.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jp.getCurrentName();
                current = jp.nextToken();// move from field name to field value
                if (fieldName.equals("ipv4") || fieldName.equals("ipv6")) {
                    current = jp.nextToken();// move from field name to field value
                    current = jp.nextToken();// move from field name to field value
                    if (current == JsonToken.START_ARRAY) {
                        System.out.println("Start array");
                        // For each of the records in the array
                        while (jp.nextToken() != JsonToken.END_ARRAY) {
                            // read the record into a tree model,
                            // this moves the parsing position to the end of it
                            JsonNode node = jp.readValueAsTree();
                            String field = jp.getCurrentName();
                            // And now we have random access to everything in the object
                            VRFRoute newRoute = new VRFRoute();
                            L2Forward newSwitch = new L2Forward();
                            newRoute.setSourceAddress(node.get("sourceAddress").getValueAsText());
                            newRoute.setDestinationAddress(node.get("destinationAddress").getValueAsText());
                            newSwitch.setInputPort(Integer.parseInt(node.get("switchInfo").getPath("inputPort").getValueAsText()));
                            newSwitch.setOutputPort(Integer.parseInt(node.get("switchInfo").getPath("outputPort").getValueAsText()));
                            newSwitch.setDPID(node.get("switchInfo").getPath("dpid").getValueAsText());
                            newRoute.setSwitchInfo(newSwitch);
                            routes.add(newRoute);
                        }
                    } else {
                        response = "Error: records should be an array: skipping.";
                        jp.skipChildren();
                    }
                } else {
                    response = "Unprocessed property: " + fieldName;
                    jp.skipChildren();
                }
            }
            return Response.ok(routes).build();

        } catch (IOException ex) {
System.out.println("IOEXception "+ex.getMessage());
        }
        return Response.status(404).entity("Some error. Check the file. Possible error: " + response).build();
    }
}

