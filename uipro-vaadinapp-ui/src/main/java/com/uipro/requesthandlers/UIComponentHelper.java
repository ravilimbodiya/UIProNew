package com.uipro.requesthandlers;

import com.uipro.entity.ComponentDetail;
import com.uipro.entity.UiproRequest;
import com.uipro.utility.Constants;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class UIComponentHelper {
	public static ComponentDetail parseComponentFromRequest(UiproRequest reqObj) {
		ComponentDetail cdObject = new ComponentDetail();

		Alignment alignment = parseComponentAlignment(reqObj);
		Component component = parseComponentProp(reqObj);

		cdObject.setAlignment(alignment);
		cdObject.setComponent(component);

		return cdObject;

	}
	
	private static Component parseComponentProp(UiproRequest reqObj) {
		String element = reqObj.getElement().toLowerCase();
		Component c = null;

		switch (element) {
		case Constants.BUTTON:
			c = new Button();
			fillButtonProperties(reqObj, c);
			break;
		case Constants.TEXTFIELD:
			c = new TextField();
			fillTextFieldProperties(reqObj, c);
			break;
		case Constants.CHECKBOX:
			c = new CheckBox();
			fillCheckboxProperties(reqObj, c);
			break;
		case Constants.LABEL:
			c = new Label();
			fillLabelProperties(reqObj, c);
			break;
		case Constants.DROPDOWN:
			break;
		}
		return c;
	}

	private static void fillLabelProperties(UiproRequest reqObj, Component label) {
		label.setCaption(reqObj.getElementValue());
		((AbstractComponent) label).setResponsive(true);
		label.setEnabled(true);
		label.setVisible(true);
	}

	private static void fillTextFieldProperties(UiproRequest reqObj, Component textField) {
		String textFieldLabel = reqObj.getElementValue();
		if(textFieldLabel != null && textFieldLabel.length() > 0) {
			textField.setCaption(textFieldLabel);			
		}
		textField.setEnabled(true);
		textField.setVisible(true);
	}
	
	private static void fillCheckboxProperties(UiproRequest reqObj, Component checkbox) {
		String checkboxLabel = reqObj.getElementValue();
		if(checkboxLabel != null && checkboxLabel.length() > 0) {
			checkbox.setCaption(checkboxLabel);			
		}
		checkbox.setEnabled(true);
		checkbox.setVisible(true);
	}

	private static void fillButtonProperties(UiproRequest reqObj, Component button) {
		String buttonLabel = reqObj.getElementValue();
		if (buttonLabel == null || buttonLabel.length() == 0) {
			button.setCaption(Constants.BUTTONLABEL);
		} else {
			button.setCaption(buttonLabel);
		}

		button.setId(reqObj.getElementId());
		((AbstractComponent) button).setResponsive(true);
		button.setEnabled(true);
		button.setVisible(true);
	}

	private static Alignment parseComponentAlignment(UiproRequest reqObj) {
		String elementPosition = reqObj.getElementPosition();
		Alignment a = Constants.ALIGNMENT;

		if (elementPosition != null && elementPosition.length() > 0) {
			switch (elementPosition.toLowerCase()) {
			case "top_left":
				return Alignment.TOP_LEFT;
			case "top_right":
				return Alignment.TOP_RIGHT;
			case "top_center":
				return Alignment.TOP_CENTER;
			case "middle_left":
				return Alignment.MIDDLE_LEFT;
			case "middle_right":
				return Alignment.MIDDLE_RIGHT;
			case "bottom_left":
				return Alignment.BOTTOM_LEFT;
			case "bottom_center":
				return Alignment.BOTTOM_CENTER;
			case "bottom_right":
				return Alignment.BOTTOM_RIGHT;
			case "middle_center":
				return Alignment.MIDDLE_CENTER;
			case "left":
				return Alignment.TOP_LEFT;
			case "right":
				return Alignment.TOP_RIGHT;
			case "center":
				return Alignment.TOP_CENTER;
			case "middle":
				return Alignment.MIDDLE_CENTER;
			default:
				return a;
			}
		} else
			return a;
	}

}
