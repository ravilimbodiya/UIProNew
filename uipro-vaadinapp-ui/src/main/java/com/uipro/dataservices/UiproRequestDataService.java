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

    private UiproRequestDataService(UiproRequest reqObj) {
    	setRequestData(reqObj);
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
	
	public synchronized static void setInstance(UiproRequest reqObj) {
         INSTANCE = new UiproRequestDataService(reqObj);
    }

}
