package com.uipro.dataservices;

import java.io.Serializable;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5577917480656457934L;

	public static DataService get() {
        return UiproRequestDataService.getInstance();
    }
    
    public static void set(int uid, boolean isNewPage, String template, String element, String elementType,
			String elementName, String elementId, String elementPosition, String elementColor, String elementValue) {
        UiproRequestDataService.setInstance(uid, isNewPage, template, element, elementType, elementName, elementId, elementPosition, elementColor, elementValue);
    }
    
    public static void clear() {
        UiproRequestDataService.clearInstance();
    }

}
