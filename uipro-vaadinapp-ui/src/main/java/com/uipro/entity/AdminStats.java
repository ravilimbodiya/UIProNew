package com.uipro.entity;

public class AdminStats {
	private int totalUsers;
	private int totalActiveUsers;
	private int blockedUsers;
	private int totalDesignsCreated;
	public int getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}
	public int getTotalActiveUsers() {
		return totalActiveUsers;
	}
	public void setTotalActiveUsers(int totalActiveUsers) {
		this.totalActiveUsers = totalActiveUsers;
	}
	public int getTotalDesignsCreated() {
		return totalDesignsCreated;
	}
	public void setTotalDesignsCreated(int totalDesignsCreated) {
		this.totalDesignsCreated = totalDesignsCreated;
	}
	public int getBlockedUsers() {
		return blockedUsers;
	}
	public void setBlockedUsers(int blockedUsers) {
		this.blockedUsers = blockedUsers;
	}
}
