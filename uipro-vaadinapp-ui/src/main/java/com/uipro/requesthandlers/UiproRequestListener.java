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

		String uid = (String) reqParamsJson.get("uid");
		if (uid != null && uid.length() > 0) {
			reqObj.setUid(Integer.parseInt(uid));
		}

		String element = (String) reqParamsJson.get("element");
		if (element != null && element.length() > 0) {
			reqObj.setElement(element);
		}

		String elemColor = (String) reqParamsJson.get("elementColor");
		if (elemColor != null && elemColor.length() > 0) {
			reqObj.setElementColor(elemColor);
		}

		String elemName = (String) reqParamsJson.get("elementName");
		if (elemName != null && elemName.length() > 0) {
			reqObj.setElementName(elemName);
		}

		String elemType = (String) reqParamsJson.get("elementType");
		if (elemType != null && elemType.length() > 0) {
			reqObj.setElementType(elemType);
		}

		String elemVal = (String) reqParamsJson.get("elementValue");
		if (elemVal != null) {
			reqObj.setElementValue(elemVal);
		}

		String isLastReq = (String) reqParamsJson.get("isLastRequest");
		if (isLastReq != null && isLastReq.length() > 0) {
			reqObj.setNewPage(Boolean.getBoolean(isLastReq));
		}

		String template = (String)reqParamsJson.get("template");
		if(template != null && template.length() > 0) {
			reqObj.setTemplate(template);
		} 

		String elemId = (String) reqParamsJson.get("elementId");
		if(elemId != null && elemId.length() > 0) {
			reqObj.setElementId(elemId);
		}

		String elemPos = (String) reqParamsJson.get("elementPosition");
		if (elemPos != null) {
			reqObj.setElementPosition(elemPos);
		}

		return reqObj;
	}
}
