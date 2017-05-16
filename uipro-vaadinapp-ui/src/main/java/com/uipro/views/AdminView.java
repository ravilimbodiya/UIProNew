package com.uipro.views;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.uipro.entity.AdminStats;
import com.uipro.utility.DbUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

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
		stats.getTotalUsers();
	}

	private AdminStats getAdminStats() {

		AdminStats stats = new AdminStats();

		int totalUsers = getTotalUsers();
		int totalActiveUsers = getTotalActiveUsers();
		int getTotalDesigns = getTotalDesigns();

		stats.setTotalActiveUsers(totalActiveUsers);
		stats.setTotalDesignsCreated(getTotalDesigns);
		stats.setTotalUsers(totalUsers);

		return stats;
	}

	private int getTotalDesigns() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getTotalActiveUsers() {
		int totalActiveUsers = 0;
		
		
		return 0;
	}

	private int getTotalUsers() {
		int totalUsers = 0;

		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");
		totalUsers = (int) userColl.count();

		return totalUsers;
	}

}
