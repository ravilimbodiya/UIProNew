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
    private String template;
    private String element;
    private String elementType;
    private String elementName;
    private String elementId;
    private String elementPosition;
    private String elementColor;
    private String elementValue;
    
    public UiproRequest() {
	}
    
	public UiproRequest(int uid, boolean isNewPage, String template, String element, String elementType,
			String elementName, String elementId, String elementPosition, String elementColor, String elementValue) {
		super();
		this.uid = uid;
		this.isNewPage = isNewPage;
		this.template = template;
		this.element = element;
		this.elementType = elementType;
		this.elementName = elementName;
		this.elementId = elementId;
		this.elementPosition = elementPosition;
		this.elementColor = elementColor;
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

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
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

	public String getElementColor() {
		return elementColor;
	}

	public void setElementColor(String elementColor) {
		this.elementColor = elementColor;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}
    
}
