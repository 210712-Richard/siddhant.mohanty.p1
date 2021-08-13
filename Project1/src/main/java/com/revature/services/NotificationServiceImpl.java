package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.data.NotificationDAO;
import com.revature.data.NotificationDAOImpl;
import com.revature.factory.Log;

@Log
public class NotificationServiceImpl implements NotificationService {

	private NotificationDAO nd = new NotificationDAOImpl();
	
	@Override
	public void notify(Employee emp, String message) {
		nd.updateNotifications(emp.getUsername(), message);
	}

	@Override
	public List<String> checkNotifications(Employee emp) {
		List<String> notifications = nd.getNotifications(emp.getUsername());
		nd.clearNotifications(emp.getUsername());
		return notifications;
	}

}
