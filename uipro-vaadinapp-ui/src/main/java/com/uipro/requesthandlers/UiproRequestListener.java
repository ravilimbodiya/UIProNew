package com.uipro.requesthandlers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.uipro.dataservices.DataService;
import com.uipro.entity.UiproRequest;

/**
 * Application Lifecycle Listener implementation class UiproRequestListener
 *
 */
@WebServlet(urlPatterns = "/uiprolistner", name = "UiproRequestListener", asyncSupported = true)
public class UiproRequestListener extends HttpServlet {

	private static final long serialVersionUID = -1571699010528307414L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Reading POST request Parameters
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			reader.close();

			JSONParser parser = new JSONParser();
			JSONObject jsonObj;

			jsonObj = (JSONObject) parser.parse(jb.toString());
			UiproRequest reqObj = convertJsonToDataObject(jsonObj);

			DataService.set(reqObj);

			System.out.println("Successfully converted to JSON object...");
			System.out.println("\nJSON Object: " + jsonObj.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UiproRequest convertJsonToDataObject(JSONObject reqParamsJson) {
		UiproRequest reqObj = new UiproRequest();

		reqObj.setUid(Integer.parseInt(reqParamsJson.get("uid").toString()));
		reqObj.setElement(reqParamsJson.get("element").toString());
		reqObj.setElementColor(reqParamsJson.get("elementColor").toString());
		reqObj.setElementName(reqParamsJson.get("elementName").toString());
		reqObj.setElementType(reqParamsJson.get("elementType").toString());
		
		
		String elemVal = (String) reqParamsJson.get("elementValue");
		if(elemVal != null) {
			reqObj.setElementValue(elemVal);
		}
		
		reqObj.setNewPage(Boolean.getBoolean(reqParamsJson.get("isLastRequest")
				.toString()));
		reqObj.setTemplate(reqParamsJson.get("template").toString());
		reqObj.setElementId(reqParamsJson.get("elementId").toString());
		
		String elemPos = (String) reqParamsJson.get("elementPosition");
		if(elemPos != null) {
			reqObj.setElementPosition(elemPos);
		}
		
		return reqObj;
	}
}
