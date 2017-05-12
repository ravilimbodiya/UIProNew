package com.uipro.views;

import com.uipro.entity.UserProfile;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class UserProfileView extends UserProfileDesign implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6559823658093808022L;
	public static final String VIEW_NAME = "User Profile";

	@Override
	public void enter(ViewChangeEvent event) {
		monitorProfileChanges();
	}
	
	public void monitorProfileChanges() {
		submit.addClickListener(new Button.ClickListener() {			
			/**
			 * 
			 */
			private static final long serialVersionUID = -4477252122279009837L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Button has been clicked to save the profile.");
				UserProfile profile = getUserDataFromProfile();
				profile.toString();
			}
		});
	}
	

	private UserProfile getUserDataFromProfile() {
		String fn = firstName.getValue();
		String ln = lastName.getValue();
		
		String emailId = email.getValue();
		
		String cc = countryCode.getValue();
		String ph = phoneNumber.getValue();
		String phoneNumber = cc + "-" + ph;
		
		String mm = month.getValue();
		String dd = day.getValue();
		String yy = year.getValue();
		String dob = dd + "/" + mm + "/" + yy;
		
		UserProfile profile = new UserProfile(fn, ln, emailId, phoneNumber, dob);
		
		return profile;
	}

	
}
