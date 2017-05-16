package com.uipro.views;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.uipro.entity.AdminStats;
import com.uipro.entity.UserProfile;
import com.uipro.utility.DbUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

public class AdminView extends AdminDesign implements View {

	public static final String VIEW_NAME = "Admin";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250222716082287192L;

	@Override
	public void enter(ViewChangeEvent event) {
		AdminStats stats = new AdminStats();
		stats = getAdminStats();

		// set the Admin stats to the elements on the page here.
		totalUsersLabel.setValue(String.valueOf(stats.getTotalUsers()));
		totalActiveUsersLabel.setValue(String.valueOf(stats
				.getTotalActiveUsers()));
		totalDesignsLabel.setValue(String.valueOf(stats
				.getTotalDesignsCreated()));

		List<UserProfile> userList = new ArrayList<UserProfile>();
		userList = fetchUserListFromDB();
		addComponent(showUserTable(userList));

	}

	private List<UserProfile> fetchUserListFromDB() {
		List<UserProfile> userList = new ArrayList<UserProfile>();
		
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");

		try (MongoCursor<Document> cursor = userColl.find().iterator()) {
			while (cursor.hasNext()) {
				UserProfile userProfile = new UserProfile();
				
				Document doc = cursor.next();
				
				String uid = (String) doc.get("uid");
				String firstName = (String) doc.get("firstName");
				String lastName = (String) doc.get("lastName");
				String email = (String) doc.get("email");
				String designCount = (String) doc.get("designCount");
				String isActive = (String) doc.get("isActive");
				
				userProfile.setEmail(email);
				userProfile.setFirstName(firstName);
				userProfile.setLastName(lastName);
				userProfile.setDesignCount(designCount);
				userProfile.setUserid(uid);
				userProfile.setIsActive(isActive);
				
				userList.add(userProfile);
			}
		}

		return userList;
	}

	private static Table showUserTable(List<UserProfile> userList) {
		// Create a table and add a style to
		// allow setting the row height in theme.
		Table table = new Table();
		table.addStyleName("components-inside");

		/*
		 * Define the names and data types of columns. The "default value"
		 * parameter is meaningless here.
		 */
		table.addContainerProperty("UID", Label.class, null);
		table.addContainerProperty("Name", Label.class, null);
		table.addContainerProperty("Email", Label.class, null);
		table.addContainerProperty("Design Count", Label.class, null);
		table.addContainerProperty("Manage", Button.class, null);

		/* Add a few items in the table. */
		for (int i = 0; i < userList.size(); i++) {

			// Create the fields for the current table row
			Label uid = new Label(userList.get(i).getUserid());
			Label name = new Label(userList.get(i).getFirstName() + " "
					+ userList.get(i).getLastName());
			Label email = new Label(userList.get(i).getEmail());
			Label designCount = new Label(userList.get(i).getDesignCount());

			// The Table item identifier for the row.
			Integer itemId = new Integer(i);

			// Create a button and handle its click. A Button does not
			// know the item it is contained in, so we have to store the
			// item ID as user-defined data.
			
			Button manageButton = new Button();
			if(userList.get(i).getIsActive().equals("true")) {
				manageButton.setCaption("Block");
				manageButton.addStyleName("small danger");

			} else {
				manageButton.setCaption("Unblock");
				manageButton.addStyleName("small primary");
			}
			
			manageButton.setData(itemId);
			manageButton.addClickListener(new Button.ClickListener() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4921772924220930184L;

				public void buttonClick(ClickEvent event) {
					// Get the item identifier from the user-defined data.
					
					if(manageButton.getCaption().equals("Block")) {
						DbUtil.blockUser(uid.getValue());
						Notification.show("User blocked!");
					} else {
						DbUtil.unblockUser(uid.getValue());
						Notification.show("User unblocked!");
					}
					Page.getCurrent().reload();
					
				}
			});
			

			// Create the table row.
			table.addItem(
					new Object[] { uid, name, email, designCount, manageButton },
					itemId);
		}

		return table;
	}

	public static AdminStats getAdminStats() {

		AdminStats stats = new AdminStats();

		int totalUsers = getTotalUsers();
		int totalActiveUsers = getTotalActiveUsers();
		int getTotalDesigns = getTotalDesigns();

		stats.setTotalActiveUsers(totalActiveUsers);
		stats.setTotalDesignsCreated(getTotalDesigns);
		stats.setTotalUsers(totalUsers);

		return stats;
	}

	private static int getTotalDesigns() {
		int totalDesigns = 0;

		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");

		try (MongoCursor<Document> cursor = userColl.find().iterator()) {
			while (cursor.hasNext()) {
				String totalDesignCount = (String) cursor.next().get(
						"designCount");
				totalDesigns += Integer.valueOf(totalDesignCount);
			}
		}

		System.out.println("Total designs:" + totalDesigns);
		return totalDesigns;
	}

	private static int getTotalActiveUsers() {
		int totalActiveUsers = 0;
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("isActive", "true");
		totalActiveUsers = (int) userColl.count(whereQuery);

		return totalActiveUsers;
	}

	private static int getTotalUsers() {
		int totalUsers = 0;

		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");
		totalUsers = (int) userColl.count();

		return totalUsers;
	}

}
