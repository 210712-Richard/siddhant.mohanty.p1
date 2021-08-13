package com.revature.data;

import java.util.List;

public interface NotificationDAO {
	
	void createWelcomeNotification(String recipient);
	
	/**
	 * Adds a notification to the database
	 * 
	 * @param recipient The employee whom the notification is addressed to
	 * @param message The actual body of the notification
	 */
	
	void updateNotifications(String recipient, String message);
	
	void clearNotifications(String recipient);
	
	/**
	 * Gets all the notifications that an employee has
	 * 
	 * @param recipient The employee whose notifications are being obtained
	 * @return A list of all the notifications that employee has
	 */
	
	List<String> getNotifications(String recipient);
	
}
