package com.uipro.views;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.uipro.authentication.CurrentUser;
import com.uipro.entity.UserProfile;
import com.uipro.utility.DbUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;

public class UserProfileView extends UserProfileDesign implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6559823658093808022L;
	public static final String VIEW_NAME = "User Profile";

	@Override
	public void enter(ViewChangeEvent event) {
		UserProfile profile = readUserProfileFromDb();

		if (profile != null)
			fillProfileForm(profile);

		monitorProfileChanges();
	}

	private void fillProfileForm(UserProfile profile) {
		String fn = profile.getFirstName();
		if (fn != null && fn.length() > 0) {
			this.firstName.setValue(fn);
		}

		String ln = profile.getLastName();
		if (ln != null && ln.length() > 0) {
			this.lastName.setValue(ln);
		}

		String email = profile.getEmail();
		if (email != null && email.length() > 0) {
			this.email.setValue(email);
		}

		String phone = profile.getPhoneNo();
		if (phone != null && phone.length() > 0) {
			this.countryCode.setValue(profile.getPhoneNo().split("-")[0]);
			this.phoneNumber.setValue(profile.getPhoneNo().split("-")[1]);
		}

		String dob = profile.getDob();
		if (dob != null && dob.length() > 0) {
			day.setValue(profile.getDob().split("/")[1]);
			month.setValue(profile.getDob().split("/")[0]);
			year.setValue(profile.getDob().split("/")[2]);
		}
	}

	public void monitorProfileChanges() {
		submit.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4477252122279009837L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out
						.println("Button has been clicked to save the profile.");
				UserProfile profile = getUserDataFromProfile();
				profile.toString();
				saveUserProfileInDb(profile);
				showNotification(new Notification("Success!",
	                    "Your profile information is updated.",
	                    Notification.Type.HUMANIZED_MESSAGE));
			}

		});
	}

	private void saveUserProfileInDb(UserProfile profile) {
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");

		String uid = CurrentUser.get();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uid", uid);

		BasicDBObject updateFields = new BasicDBObject();
		updateFields.append("firstName", profile.getFirstName());
		updateFields.append("lastName", profile.getLastName());
		updateFields.append("email", profile.getEmail());
		updateFields.append("dob", profile.getDob());
		updateFields.append("phoneNo", profile.getPhoneNo());		
		
		BasicDBObject searchQuery = new BasicDBObject().append("uid", uid);
		
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", updateFields);

		userColl.updateMany(searchQuery, setQuery);
	}

	private UserProfile readUserProfileFromDb() {
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");

		String userId = CurrentUser.get();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uid", userId);

		FindIterable<Document> cursor = userColl.find(whereQuery);
		Document document = cursor.first();
		if (document != null && !document.isEmpty()) {
			String fn = (String) document.get("firstName");
			String ln = (String) document.get("lastName");
			String dob = (String) document.get("dob");
			String email = (String) document.get("email");
			String phNo = (String) document.get("phoneNo");

			UserProfile profile = new UserProfile(fn, ln, email, phNo, dob);
			return profile;
		}
		return null;
	}
	
	private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
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
