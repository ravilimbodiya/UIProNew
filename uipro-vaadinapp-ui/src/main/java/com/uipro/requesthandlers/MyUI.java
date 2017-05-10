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
import com.uipro.views.ContactUsView;
import com.uipro.views.FooterView;
import com.uipro.views.HeaderView;
import com.uipro.views.MainScreen;
import com.uipro.views.SimpleLoginView;
import com.uipro.views.TestView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.UIEvents;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
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
		globalLayout.setSizeFull();
		globalLayout.addStyleName("custom-scrollable-layout");
		globalLayout.addStyleName("class-for-download");
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
					if(uiproReqObj.isNewPage()){
						//Remove all existing components from the layout
						globalLayout.removeAllComponents();
					} else if (uiproReqObj.getTemplate() == null || uiproReqObj.getTemplate().equalsIgnoreCase("")) {
						//if user asked some custom components
						ComponentDetail cd = UIComponentHelper.parseComponentFromRequest(uiproReqObj);
						addComponentToUI(cd);
					} else {
						//Removing previous components to load newly requested theme.
						globalLayout.removeAllComponents();
						// draw the specified template for him
						ComponentDetail cdObject = new ComponentDetail();
						if(uiproReqObj.getTemplate().equalsIgnoreCase("login")){
							cdObject.setLoginViewTemplate(new SimpleLoginView());
							cdObject.setAlignment(Alignment.TOP_CENTER);
						} else if(uiproReqObj.getTemplate().equalsIgnoreCase("register")){
							cdObject.setTestViewTemplate(new TestView());
							cdObject.setAlignment(Alignment.TOP_CENTER);
						} else if(uiproReqObj.getTemplate().equalsIgnoreCase("header")){
							cdObject.setHeaderViewTemplate(new HeaderView());
							cdObject.setAlignment(Alignment.TOP_CENTER);
						} else if(uiproReqObj.getTemplate().equalsIgnoreCase("footer")){
							cdObject.setFooterViewTemplate(new FooterView());
							cdObject.setAlignment(Alignment.TOP_CENTER);
						} else if(uiproReqObj.getTemplate().equalsIgnoreCase("contactus")){
							cdObject.setContactUsViewTemplate(new ContactUsView());
							cdObject.setAlignment(Alignment.TOP_CENTER);
						}
						addComponentToUI(cdObject);
					}

					DataService.clear();
				}
			}
		});
	}

	// this fn will take an component detail object and put that on the layout
	public void addComponentToUI(ComponentDetail cd) {
		globalLayout.addComponent(cd.getComponent());
		globalLayout.setComponentAlignment(cd.getComponent(), cd.getAlignment());
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
