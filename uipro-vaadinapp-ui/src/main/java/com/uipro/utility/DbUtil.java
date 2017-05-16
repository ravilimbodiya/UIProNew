package com.uipro.utility;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DbUtil {
	private static String DATABASE_NAME = "uipro295database";
	private static String USER = "ravi";
	private static String PASSWORD = "mLabs123";
	private static String DB_SERVER_URL = "ds161890.mlab.com:61890";
	private static String FULL_URL = USER+":"+PASSWORD+"@"+DB_SERVER_URL+"/"+DATABASE_NAME;
	private static MongoClient mongoClient = null;
	
	public static MongoCollection<Document> getCollection(String collectionName){
		MongoClientURI uri = new MongoClientURI("mongodb://"+FULL_URL);
		mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("uipro295database");
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}
	
	public static void releaseResources(){
		mongoClient.close();
	}
	
	public static void updateDesignCountForUser(String uid) {
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil
				.getCollection("uipro_users_info");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uid", uid);
		
		String designCount = (String) userColl.find(whereQuery).first().get("designCount");
		int currDesignCount = Integer.valueOf(designCount);
		
		System.out.println("Current design count ="+ currDesignCount);
		
		int newDesignCount = currDesignCount + 1;
		
		BasicDBObject updateFields = new BasicDBObject();
		updateFields.append("designCount", String.valueOf(newDesignCount));
		
		BasicDBObject searchQuery = new BasicDBObject().append("uid", uid);
		
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", updateFields);

		userColl.updateOne(searchQuery, setQuery);

	}
}
