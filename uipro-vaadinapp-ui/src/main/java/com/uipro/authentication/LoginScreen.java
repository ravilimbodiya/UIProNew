package com.uipro.authentication;

import static com.mongodb.client.model.Filters.eq;

import java.io.Serializable;
import java.util.Random;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.uipro.entity.UserProfile;
import com.uipro.utility.DbUtil;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6053693865983225782L;
	private TextField userid;
	private TextField firstName;
	private TextField lastName;
	private TextField email;
    //private Label uiproLabel;
    private PasswordField password;
    private Button login;
    private Button signup;
    private Button signupButton;
    private LoginListener loginListener;
    private AccessControl accessControl;
    Component loginFormComponent;
    Component signupFormComponent;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        userid.focus();
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        loginFormComponent  = buildLoginForm();
        signupFormComponent = buildSignupForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginFormComponent);
        centeringLayout.setComponentAlignment(loginFormComponent,
                Alignment.MIDDLE_CENTER);
        
        centeringLayout.addComponent(signupFormComponent);
        centeringLayout.setComponentAlignment(signupFormComponent,
                Alignment.MIDDLE_CENTER);
        signupFormComponent.setVisible(false);

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);
        //loginForm.addComponent(uiproLabel = new Label("UIPro"));
        loginForm.addComponent(userid = new TextField("User ID"));
        userid.setWidth(15, Unit.EM);
        //loginForm.addComponent(password = new PasswordField("Password"));
        //password.setWidth(15, Unit.EM);
        //password.setDescription("Please ");
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        buttons.addComponent(login = new Button("Login"));
        //login.setDisableOnClick(true);
        login.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1364534671489651010L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_DANGER);

        buttons.addComponent(signupButton = new Button("Don't have User ID? "));
        signupButton.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -8580559237140997724L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
				loginFormComponent.setVisible(false);
				signupFormComponent.setVisible(true);
            }
        });
        signupButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        return loginForm;
    }
    
    private Component buildSignupForm() {
        FormLayout signupForm = new FormLayout();

        signupForm.addStyleName("login-form");
        signupForm.setSizeUndefined();
        signupForm.setMargin(false);
        //loginForm.addComponent(uiproLabel = new Label("UIPro"));
        signupForm.addComponent(firstName = new TextField("First Name"));
        firstName.setWidth(15, Unit.EM);
        signupForm.addComponent(lastName = new TextField("Last Name"));
        lastName.setWidth(15, Unit.EM);
        signupForm.addComponent(email = new TextField("Email ID"));
        email.setWidth(15, Unit.EM);
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        signupForm.addComponent(buttons);

        buttons.addComponent(signup = new Button("Signup"));
        signup.setDisableOnClick(true);
        signup.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1364534671489651010L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    String userId = signup();
                    userid.setValue(userId);
                    loginFormComponent.setVisible(true);
    				signupFormComponent.setVisible(false);
                } finally {
                	signup.setEnabled(true);
                }
            }
        });
        signup.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signup.addStyleName(ValoTheme.BUTTON_DANGER);

        buttons.addComponent(login = new Button("Already have User ID?"));
        login.addClickListener(new Button.ClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -8580559237140997724L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
				loginFormComponent.setVisible(true);
				signupFormComponent.setVisible(false);
            }
        });
        login.addStyleName(ValoTheme.BUTTON_PRIMARY);
        return signupForm;
    }

    private String getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		Integer randNum =  r.nextInt((max - min) + 1) + min;
		String randNumStr = randNum.toString();
		return randNumStr;
	}
    
    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>Welcome to UIPro</h1>"
                        + "Create your designs by just speaking to Amazon Alexa Echo",
                ContentMode.HTML);
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void login() {
        if (accessControl.signIn(userid.getValue())) {
            loginListener.loginSuccessful();
        } else {
            showNotification(new Notification("Login failed",
                    "Please check your user ID and try again.",
                    Notification.Type.HUMANIZED_MESSAGE));
            userid.focus();
        }
    }

	private String signup() {
		String userid = getRandomNumberInRange(100000, 999999);
		//Checking user id validity
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil.getCollection("uipro_users_info");

		while(userColl.count(eq("uid", userid)) > 0){
			userid = getRandomNumberInRange(100000, 999999);
		} 
		Document doc = new Document();
		doc.append("uid", userid);
		doc.append("username", firstName.getValue()+"_"+lastName.getValue());
		doc.append("firstName", firstName.getValue());
		doc.append("lastName", lastName.getValue());
		doc.append("email", email.getValue());
		doc.append("phoneNo", "");
		doc.append("dob", "");
		doc.append("isActive", "true");
		doc.append("designCount", "0");
		
		userColl.insertOne(doc);
		DbUtil.releaseResources();
		showNotification(new Notification("Success! Your User ID is: "+userid,
                "Please save this User ID for login to UIPro portal.",
                Notification.Type.TRAY_NOTIFICATION));
		return userid;
	}

    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
