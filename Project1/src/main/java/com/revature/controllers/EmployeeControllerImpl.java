package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.factory.BeanFactory;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImpl;

import io.javalin.http.Context;

public class EmployeeControllerImpl implements EmployeeController {
	
	private Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
	private EmployeeService es = (EmployeeService) BeanFactory.getFactory().get(EmployeeService.class, EmployeeServiceImpl.class);
	
	@Override
	public void login(Context ctx) {
		
	}

	@Override
	public void logout(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewNotifications(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearNotifications(Context ctx) {
		// TODO Auto-generated method stub

	}

}
