package com.uipro.requesthandlers;



import javax.servlet.annotation.WebServlet;

import com.uipro.authentication.AccessControl;
import com.uipro.authentication.BasicAccessControl;
import com.uipro.authentication.LoginScreen;
import com.uipro.authentication.LoginScreen.LoginListener;
import com.uipro.dataservices.DataService;
import com.uipro.dataservices.UiproRequestDataService;
import com.uipro.entity.ComponentDetail;
import com.uipro.entity.UiproRequest;
import com.uipro.utility.Constants;
import com.uipro.views.MainScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.UIEvents;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */

@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("com.vaadin.uipro_vaadinapp.MyAppWidgetset")
public class MyUI extends UI {

	private static final long serialVersionUID = -7517282906277130464L;
	private AccessControl accessControl = new BasicAccessControl();
	private static VerticalLayout globalLayout = new VerticalLayout();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		System.out.println("Vaadin UI init");

		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("UIPro");

		// Check user validity
		if (!accessControl.isUserSignedIn()) {
			setContent(new LoginScreen(accessControl, new LoginListener() {
				private static final long serialVersionUID = -5006228254614709162L;

				@Override
				public void loginSuccessful() {
					showMainView();
				}
			}));
		} else {
			showMainView();
		}

		setPollInterval(Constants.POLL_DURATION);

		addPollListener(new UIEvents.PollListener() {
			private static final long serialVersionUID = 7485077668520630312L;

			@Override
			public void poll(UIEvents.PollEvent event) {
				UiproRequestDataService uiproRequestServiceObj = (UiproRequestDataService) DataService
						.getNewRequestData();
				if (uiproRequestServiceObj != null) {
					UiproRequest uiproReqObj = uiproRequestServiceObj
							.getRequestData();
					ComponentDetail cd = parseComponentFromRequest(uiproReqObj);
					addComponentToUI(cd);
					DataService.clear();
				}
			}
		});
	}

	private ComponentDetail parseComponentFromRequest(UiproRequest reqObj) {
		ComponentDetail cdObject = new ComponentDetail();

		Alignment alignment = parseComponentAlignment(reqObj);
		Component component = parseComponentProp(reqObj);

		cdObject.setAlignment(alignment);
		cdObject.setComponent(component);

		return cdObject;

	}

	private Component parseComponentProp(UiproRequest reqObj) {
		String elementType = reqObj.getElementType().toLowerCase();
		Component c = null;

		switch (elementType) {
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
			break;
		case Constants.LABEL:
			c = (Component) new Label();
			fillLabelProperties(reqObj, c);
			break;
		case Constants.DROPDOWN:
			break;
		}
		return c;
	}

	private void fillLabelProperties(UiproRequest reqObj, Component label) {
		label.setCaption(reqObj.getElementValue());
	}

	private void fillTextFieldProperties(UiproRequest reqObj, Component c) {

	}

	private void fillButtonProperties(UiproRequest reqObj, Component button) {
		String buttonLabel = reqObj.getElementValue();
		if (buttonLabel == null || buttonLabel.length() == 0) {
			button.setCaption(Constants.BUTTONLABEL);
		} else {
			button.setCaption(buttonLabel);
		}

		button.setId(reqObj.getElementId());
		((AbstractComponent) button).setResponsive(true);
	}

	private Alignment parseComponentAlignment(UiproRequest reqObj) {
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
			default:
				return a;
			}
		} else
			return a;
	}

	// this fn will take an component detail object and put that on the layout
	public void addComponentToUI(ComponentDetail cd) {
		globalLayout.addComponent(cd.getComponent());
		globalLayout
				.setComponentAlignment(cd.getComponent(), cd.getAlignment());
	}

	protected void showMainView() {
		addStyleName(ValoTheme.UI_WITH_MENU);
		setContent(new MainScreen(MyUI.this));
		getNavigator().navigateTo(getNavigator().getState());
	}

	public static MyUI get() {
		return (MyUI) UI.getCurrent();
	}

	public static VerticalLayout getGlobalLayout() {
		return globalLayout;
	}

	public AccessControl getAccessControl() {
		return accessControl;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 5252033054729474690L;

	}
}
