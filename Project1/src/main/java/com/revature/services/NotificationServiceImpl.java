package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.data.NotificationDAO;
import com.revature.data.NotificationDAOImpl;

public class NotificationServiceImpl implements NotificationService {

	private NotificationDAO nd = new NotificationDAOImpl();
	
	@Override
	public void notify(Employee emp, String message) {
		nd.addNotification(emp.getUsername(), message);
	}

	@Override
	public List<String> checkNotifications(Employee emp) {
		return nd.getNotifications(emp.getUsername());
	}

}
