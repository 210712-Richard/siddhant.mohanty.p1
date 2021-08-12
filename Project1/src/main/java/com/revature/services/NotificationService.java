package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;

public interface NotificationService {

	/**
	 * Sends an employee a notification
	 * 
	 * @param emp     the employee
	 * @param message Notification message
	 */

	void notify(Employee emp, String message);

	/**
	 * Allows an employee to check their notifications. Notifications are
	 * automatically cleared after checking
	 * 
	 * @param emp the employee
	 */

	List<String> checkNotifications(Employee emp);
	
}
