package com.uipro.dataservices;

import java.io.Serializable;

import com.uipro.entity.UiproRequest;

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
    
    public static void set(UiproRequest reqObj) {
        UiproRequestDataService.setInstance(reqObj);
    }
    
    public static void clear() {
        UiproRequestDataService.clearInstance();
    }

}
