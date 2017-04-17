package com.uipro.requesthandlers;

import javax.servlet.annotation.WebServlet;

import com.uipro.authentication.AccessControl;
import com.uipro.authentication.BasicAccessControl;
import com.uipro.authentication.LoginScreen;
import com.uipro.authentication.LoginScreen.LoginListener;
import com.uipro.dataservices.DataService;
import com.uipro.dataservices.UiproRequestDataService;
import com.uipro.entity.UiproRequest;
import com.uipro.views.MainScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.UIEvents;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
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
//@Push(PushMode.MANUAL)
public class MyUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	System.out.println("Vaadin UI init");
    	//COMMENTED FOR NOW. THIS IS THE ALTERNATIVE WAY OF SYNCHORNIZING HTTP AND VAADIN REQUESTS USING A TEMP FILE
//    	String webInfPath  = VaadinServlet.getCurrent().getServletContext().getRealPath("/WEB-INF");
//		String filePath = webInfPath+"\\rpHolder.txt";
		
//		 try {
			// wrap a BufferedReader around FileReader
//			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
//			// use the readLine method of the BufferedReader to read one line at a time.
//			// the readLine method returns null when there is nothing else to read.
//			String line, line1 = "";
//			while ((line = bufferedReader.readLine()) != null)
//			{
//			    line1 += line;
//			}
//			// close the BufferedReader when we're done
//			bufferedReader.close();
//			JSONParser parser = new JSONParser();
//			JSONObject jsonObj = (JSONObject) parser.parse(line1);
			
			//Content
			//setContent(new Button((String) jsonObj.get("p1")));
			
			//this.refresh(vaadinRequest);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
        //Check user validity
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    	//Client polls server every 2 seconds to see if there is new changes in this UI
    	setPollInterval(2000);
		addPollListener(new UIEvents.PollListener() {
			@Override
			public void poll(UIEvents.PollEvent event) {
				System.out.println("Inside pollling listener.");
				UiproRequestDataService uiproRequestServiceObj = (UiproRequestDataService) DataService.get();
				if (uiproRequestServiceObj != null) {
					UiproRequest uiproReqObj = uiproRequestServiceObj.getRequestData();
					addComponentToUI(uiproReqObj);
				}
			}
		});
    }
    
    public void addComponentToUI(UiproRequest uiproReqObj) {
    	if(uiproReqObj.getElement().equalsIgnoreCase("button")){
    		Button newButton = new Button();
    		newButton.setCaption(uiproReqObj.getElementValue());
    		newButton.setId(uiproReqObj.getElementId());
    		newButton.setResponsive(true);
    		setContent(newButton);
    	}
	}

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	
    }
}
