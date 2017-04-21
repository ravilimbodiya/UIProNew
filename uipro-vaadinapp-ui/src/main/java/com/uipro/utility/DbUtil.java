package com.uipro.utility;

import org.bson.Document;

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
}
