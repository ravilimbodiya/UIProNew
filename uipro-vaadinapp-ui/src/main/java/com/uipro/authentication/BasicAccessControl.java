package com.uipro.authentication;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.uipro.utility.DbUtil;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2139681530897933030L;

	@Override
	public boolean signIn(String userid) {
		if (userid == null || userid.isEmpty())
            return false;
		//Checking user id validity
		MongoCollection<Document> userColl = (MongoCollection<Document>) DbUtil.getCollection("uipro_users_info");
		
		if(userColl.count(eq("uid", userid)) == 1){
			// User is valid
			CurrentUser.set(userid);
			DbUtil.releaseResources();
	        return true;
		} else {
			DbUtil.releaseResources();
			return false;
		}
        
	}
	
	@Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;

        CurrentUser.set(username);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

	

}
