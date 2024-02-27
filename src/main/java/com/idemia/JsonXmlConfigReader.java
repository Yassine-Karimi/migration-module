package com.idemia;import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class JsonXmlConfigReader {

    public static void main(String[] args) {
        try {
            // Path to the JSON file containing configurations
            File jsonFile = new File("src/main/resources/alphaConfig-J.json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Path to the XML file containing customization data
            File xmlFile = new File("src/main/resources/alphaConfig-J.xml");
            Document xmlDoc = parseXmlFile(xmlFile);

            // Update JSON attributes using XML values
            updateJsonAttributes(jsonNode, xmlDoc);

            // Output the updated JSON object
            System.out.println("Updated JSON object:");
            System.out.println(jsonNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document parseXmlFile(File xmlFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void updateJsonAttributes(JsonNode jsonNode, Document xmlDoc) {
        jsonNode.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            String xmlValue = getNodeValue(xmlDoc, key);

            if (xmlValue != null) {
                ((ObjectNode) jsonNode).put(key, xmlValue);
            } else {
                System.out.println("No value found for XML element: " + key);
            }
        });
    }

    private static String getNodeValue(Document xmlDoc, String nodeName) {
        NodeList nodeList = xmlDoc.getElementsByTagName(nodeName);
        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        } else {
            System.err.println("No value found for XML element: " + nodeName);
            return null;
        }
    }
}
