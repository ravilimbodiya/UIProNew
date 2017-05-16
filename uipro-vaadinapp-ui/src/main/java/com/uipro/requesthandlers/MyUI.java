package com.uipro.requesthandlers;

import javax.servlet.annotation.WebServlet;

import com.uipro.authentication.AccessControl;
import com.uipro.authentication.BasicAccessControl;
import com.uipro.authentication.CurrentUser;
import com.uipro.authentication.LoginScreen;
import com.uipro.authentication.LoginScreen.LoginListener;
import com.uipro.dataservices.DataService;
import com.uipro.dataservices.UiproRequestDataService;
import com.uipro.entity.ComponentDetail;
import com.uipro.entity.UiproRequest;
import com.uipro.exception.UiproGenericException;
import com.uipro.utility.Constants;
import com.uipro.utility.DbUtil;
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
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;
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
	private static VerticalLayout viewerLayout = new VerticalLayout();
	private static VerticalLayout globalLayout = new VerticalLayout();
	ComponentDetail cd = null;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		System.out.println("Vaadin UI init");

		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("UIPro");
		viewerLayout.setSizeFull();
		viewerLayout.addStyleName("custom-scrollable-layout");
		viewerLayout.addStyleName("class-for-download");
		// Check user validity
		try {
			if (!accessControl.isUserSignedIn()) {
				setContent(new LoginScreen(accessControl, new LoginListener() {
					private static final long serialVersionUID = -5006228254614709162L;

					@Override
					public void loginSuccessful() {
						if(accessControl.isUserInRole("uipro_admin")){
							try {
								showAdminView();
							} catch (UiproGenericException e) {
								System.out.println("Login Success! But Exception occurred - "+e.getMessage());
								Notification.show("Something went wrong. Please refresh the page and try again.", Type.TRAY_NOTIFICATION);
							}
						} else{
							try {
								showMainView();
							} catch (UiproGenericException e) {
								System.out.println("Login Success! But Exception occurred - "+e.getMessage());
								Notification.show("Something went wrong. Please refresh the page and try again.", Type.TRAY_NOTIFICATION);
							}
						}
						
					}
				}));
			} else {
				if(accessControl.isUserInRole("uipro_admin")){
					showAdminView();
				} else{
					showMainView();
				}
			}
			viewerLayout.addComponent(globalLayout);
			
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
						} else if(uiproReqObj.isSaveRequest()){
							String APP_URL = "";
							if(Page.getCurrent().getLocation().getPort() != -1){
								//Port is available, i.e Localhost
								APP_URL = "http://"+Page.getCurrent().getLocation().getHost()+":"+Page.getCurrent().getLocation().getPort()+"/uipro-vaadinapp-ui";
							} else {
								// No Port i.e. IBM bluemix cloud URL
								APP_URL = "https://"+Page.getCurrent().getLocation().getHost();
							}
							JavaScript.getCurrent().execute("var saveAs=saveAs||function(e){'use strict';if(typeof e==='undefined'||typeof navigator!=='undefined'&&/MSIE [1-9]\\./.test(navigator.userAgent)){return}var t=e.document,n=function(){return e.URL||e.webkitURL||e},r=t.createElementNS('http://www.w3.org/1999/xhtml','a'),o='download'in r,a=function(e){var t=new MouseEvent('click');e.dispatchEvent(t)},i=/constructor/i.test(e.HTMLElement)||e.safari,f=/CriOS\\/[\\d]+/.test(navigator.userAgent),u=function(t){(e.setImmediate||e.setTimeout)(function(){throw t},0)},s='application/octet-stream',d=1e3*40,c=function(e){var t=function(){if(typeof e==='string'){n().revokeObjectURL(e)}else{e.remove()}};setTimeout(t,d)},l=function(e,t,n){t=[].concat(t);var r=t.length;while(r--){var o=e['on'+t[r]];if(typeof o==='function'){try{o.call(e,n||e)}catch(a){u(a)}}}},p=function(e){if(/^\\s*(?:text\\/\\S*|application\\/xml|\\S*\\/\\S*\\+xml)\\s*;.*charset\\s*=\\s*utf-8/i.test(e.type)){return new Blob([String.fromCharCode(65279),e],{type:e.type})}return e},v=function(t,u,d){if(!d){t=p(t)}var v=this,w=t.type,m=w===s,y,h=function(){l(v,'writestart progress write writeend'.split(' '))},S=function(){if((f||m&&i)&&e.FileReader){var r=new FileReader;r.onloadend=function(){var t=f?r.result:r.result.replace(/^data:[^;]*;/,'data:attachment/file;');var n=e.open(t,'_blank');if(!n)e.location.href=t;t=undefined;v.readyState=v.DONE;h()};r.readAsDataURL(t);v.readyState=v.INIT;return}if(!y){y=n().createObjectURL(t)}if(m){e.location.href=y}else{var o=e.open(y,'_blank');if(!o){e.location.href=y}}v.readyState=v.DONE;h();c(y)};v.readyState=v.INIT;if(o){y=n().createObjectURL(t);setTimeout(function(){r.href=y;r.download=u;a(r);h();c(y);v.readyState=v.DONE});return}S()},w=v.prototype,m=function(e,t,n){return new v(e,t||e.name||'download',n)};if(typeof navigator!=='undefined'&&navigator.msSaveOrOpenBlob){return function(e,t,n){t=t||e.name||'download';if(!n){e=p(e)}return navigator.msSaveOrOpenBlob(e,t)}}w.abort=function(){};w.readyState=w.INIT=0;w.WRITING=1;w.DONE=2;w.error=w.onwritestart=w.onprogress=w.onwrite=w.onabort=w.onerror=w.onwriteend=null;return m}(typeof self!=='undefined'&&self||typeof window!=='undefined'&&window||this.content);if(typeof module!=='undefined'&&module.exports){module.exports.saveAs=saveAs}else if(typeof define!=='undefined'&&define!==null&&define.amd!==null){define('FileSaver.js',function(){return saveAs})}"
									+ "var resources = \"<link rel='stylesheet' type='text/css' href='"+APP_URL+"/VAADIN/themes/mytheme/styles.css?v=7.7.8'>\";"
									+ "var innerHTML = \"<div class='v-app mytheme myui'>\"+document.getElementsByClassName('class-for-download')[0].innerHTML+\"</div>\";"
									+ "var contentToSave = resources + innerHTML;"
									+ "var blob = new Blob([contentToSave], {type: 'text/html;charset=utf-8'});"
									+ "saveAs(blob, 'your_design.html');"
									);
							Notification.show("Your design is saved to local disk.", Type.TRAY_NOTIFICATION);
							DbUtil.updateDesignCountForUser(CurrentUser.get());
						} else if(uiproReqObj.isRemoveLast()){
							//Remove last component from the layout
							if(globalLayout.getComponentCount() > 0){
								Component compToBeRemoved = globalLayout.getComponent(globalLayout.getComponentCount() - 1);
								globalLayout.removeComponent(compToBeRemoved);
							} else {
								Notification.show("No element to remove.", Type.TRAY_NOTIFICATION);
							}
							
						} else if (uiproReqObj.getTemplate() != null && !uiproReqObj.getTemplate().equalsIgnoreCase("")) {

							//Removing previous components to load newly requested theme.
							//globalLayout.removeAllComponents();
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
						
							
						} else if (uiproReqObj.getElement() != null){
							//if user asked some custom components
							cd = UIComponentHelper.parseComponentFromRequest(uiproReqObj);
							addComponentToUI(cd);
						}

						DataService.clear();
					}
				}
			});
		} catch (UiproGenericException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Exception occurred in MyUI- "+e.getMessage());
			Notification.show("Some error occurred. Please refresh the page and try again.", Type.HUMANIZED_MESSAGE);
		}
	}

	// this fn will take an component detail object and put that on the layout
	public void addComponentToUI(ComponentDetail cd) {
		globalLayout.addComponent(cd.getComponent());
		globalLayout.setComponentAlignment(cd.getComponent(), cd.getAlignment());
	}

	protected void showMainView() throws UiproGenericException {
		try {
			addStyleName(ValoTheme.UI_WITH_MENU);
			setContent(new MainScreen(MyUI.this));
			getNavigator().navigateTo(getNavigator().getState());
		}  catch (UiproGenericException e) {
			//e.printStackTrace();
			throw new UiproGenericException(e);
		} catch (Exception ex) {
			//e.printStackTrace();
			throw new UiproGenericException(ex);
		}
	}
	
	protected void showAdminView() throws UiproGenericException{
		try {
			addStyleName(ValoTheme.UI_WITH_MENU);
			setContent(new MainScreen(MyUI.this, true));
			getNavigator().navigateTo(getNavigator().getState());
		} catch (UiproGenericException e) {
			//e.printStackTrace();
			throw new UiproGenericException(e);
		} catch (Exception ex) {
			//e.printStackTrace();
			throw new UiproGenericException(ex);
		}
	}

	public static MyUI get() {
		return (MyUI) UI.getCurrent();
	}

	public static VerticalLayout getGlobalLayout() {
		return viewerLayout;
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
