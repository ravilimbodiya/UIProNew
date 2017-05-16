package com.uipro.views;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
		totalUsersLabel.setValue(String.valueOf(stats.getTotalUsers()));
		totalActiveUsersLabel.setValue(String.valueOf(stats.getTotalActiveUsers()));
		totalDesignsLabel.setValue(String.valueOf(stats.getTotalDesignsCreated()));
		
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
		    	String totalDesignCount = (String) cursor.next().get("designCount");
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
