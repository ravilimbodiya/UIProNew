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

/**
 * Application Lifecycle Listener implementation class UiproRequestListener
 *
 */
@WebServlet(urlPatterns = "/uiprolistner", name = "UiproRequestListener", asyncSupported = true)
public class UiproRequestListener extends HttpServlet {

	private static final long serialVersionUID = -1571699010528307414L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// super.service(request, response);
		//String webInfPath = getServletContext().getRealPath("/WEB-INF");
		//String filePath = webInfPath + "\\rpHolder.txt";
		//Reading POST request Parameters
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			reader.close();
			
			JSONParser parser = new JSONParser();
			JSONObject reqParamsJson;
			reqParamsJson = (JSONObject) parser.parse(jb.toString());
			setReqParamsAsDataObject(reqParamsJson);
			System.out.println("Successfully converted to JSON object...");
			System.out.println("\nJSON Object: " + reqParamsJson.toJSONString());
			// FileWriter file = new FileWriter(filePath);
			// file.write(reqP.toJSONString());
			// file.close();
			//ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
			//((MyUI) UI.getCurrent()).refreshPage();
			//Page.getCurrent().reload();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setReqParamsAsDataObject(JSONObject reqParamsJson) {
		DataService.set(Integer.parseInt(reqParamsJson.get("uid").toString()), 
				Boolean.getBoolean(reqParamsJson.get("isLastRequest").toString()), 
				reqParamsJson.get("template").toString(),
				reqParamsJson.get("element").toString(), 
				reqParamsJson.get("elementType").toString(), 
				reqParamsJson.get("elementName").toString(),
				reqParamsJson.get("elementId").toString(), 
				reqParamsJson.get("elementPosition").toString(), 
				reqParamsJson.get("elementColor").toString(),
				reqParamsJson.get("elementValue").toString());
	}
}
