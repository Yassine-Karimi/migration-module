package com.idemia;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonToXml {

	public static void main(String[] args) throws IOException {
		String root = "root";
		Path jsonFilePath = Paths.get("src/main/resources/alphaConfig-J.json");
		Path xmlFilePath = Paths.get("src/main/resources/alphaConfig-J.xml");

		// Read JSON data from file
		String jsonString = Files.readString(jsonFilePath);
		JSONArray jsonArray = new JSONArray(jsonString);

		// Construct XML string
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<").append(root).append(">");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			xmlBuilder.append(XML.toString(jsonObject));
		}
		xmlBuilder.append("</").append(root).append(">");

		// Write XML string to file
		Files.write(xmlFilePath, xmlBuilder.toString().getBytes());

		System.out.println("XML written to file: " + xmlFilePath);
	}
}
