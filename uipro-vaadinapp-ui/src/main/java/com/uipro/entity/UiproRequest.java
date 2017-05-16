package com.uipro.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class UiproRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2587357260513116240L;
	@NotNull
    private int uid = -1;
    private boolean isNewPage = false;
    private boolean isSaveRequest = false;
    private boolean isRemoveLast = false;
    private String template;
    private String element;
    private String elementName;
    private String elementId;
    private String elementPosition;
    private String elementValue;
    
    public UiproRequest() {
    	uid = -1;
    	isNewPage = false;
    	isSaveRequest = false;
    	isRemoveLast = false;
    	elementValue = "button";
    	template = null;
    	element = "button";
    	elementName = "button";
    	elementPosition = "top_center";
    	elementId = "button1";
	}
    
	public UiproRequest(int uid, boolean isNewPage, boolean isSaveRequest, boolean isRemoveLast, String template, String element, String elementType,
			String elementName, String elementId, String elementPosition, String elementColor, String elementValue) {
		super();
		this.uid = uid;
		this.isNewPage = isNewPage;
		this.isSaveRequest = isSaveRequest;
		this.isRemoveLast = isRemoveLast;
		this.template = template;
		this.element = element;
		this.elementName = elementName;
		this.elementId = elementId;
		this.elementPosition = elementPosition;
		this.elementValue = elementValue;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isNewPage() {
		return isNewPage;
	}

	public void setNewPage(boolean isNewPage) {
		this.isNewPage = isNewPage;
	}

	public boolean isSaveRequest() {
		return isSaveRequest;
	}

	public void setSaveRequest(boolean isSaveRequest) {
		this.isSaveRequest = isSaveRequest;
	}

	public boolean isRemoveLast() {
		return isRemoveLast;
	}

	public void setRemoveLast(boolean isRemoveLast) {
		this.isRemoveLast = isRemoveLast;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementPosition() {
		return elementPosition;
	}

	public void setElementPosition(String elementPosition) {
		this.elementPosition = elementPosition;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
    
}
