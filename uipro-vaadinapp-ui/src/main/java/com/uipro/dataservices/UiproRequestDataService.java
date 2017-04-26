package com.uipro.dataservices;

import com.uipro.entity.UiproRequest;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class UiproRequestDataService extends DataService {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7826521234862461646L;
	private static UiproRequestDataService INSTANCE;
    private UiproRequest requestData;

    private UiproRequestDataService(int uid, boolean isNewPage, String template, String element, String elementType,
			String elementName, String elementId, String elementPosition, String elementColor, String elementValue) {
    	//Setting request data into object
    	setRequestData(new UiproRequest(uid, isNewPage, template, element, elementType, elementName, elementId, elementPosition, elementColor, elementValue));
    }

    public synchronized UiproRequest getRequestData() {
		return requestData;
	}

	public synchronized void setRequestData(UiproRequest requestData) {
		this.requestData = requestData;
	}

	public synchronized static DataService getInstance() {
        return INSTANCE;
    }
	
	public synchronized static void clearInstance() {
        INSTANCE = null;
    }
	
	public synchronized static void setInstance(int uid, boolean isNewPage, String template, String element, String elementType,
			String elementName, String elementId, String elementPosition, String elementColor, String elementValue) {
         INSTANCE = new UiproRequestDataService(uid, isNewPage, template, element, elementType, elementName, elementId, elementPosition, elementColor, elementValue);
    }

}
