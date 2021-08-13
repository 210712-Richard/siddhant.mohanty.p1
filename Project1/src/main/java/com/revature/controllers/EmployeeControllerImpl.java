package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.NotificationService;
import com.revature.services.NotificationServiceImpl;

import io.javalin.http.Context;

@Log
public class EmployeeControllerImpl implements EmployeeController {

	private Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
	private EmployeeService es = (EmployeeService) BeanFactory.getFactory().get(EmployeeService.class,
			EmployeeServiceImpl.class);
	private NotificationService ns = (NotificationService) BeanFactory.getFactory().get(NotificationService.class,
			NotificationServiceImpl.class);

	@Override
	public void login(Context ctx) {
		log.trace("login method called");
		log.debug(ctx.body());
		Employee emp = ctx.bodyAsClass(Employee.class);
		log.debug(emp);
		if (es.authenticate(emp.getUsername(), emp.getPassword())) {
			emp = es.login(emp.getUsername(), emp.getPassword());
			log.debug(emp);
		} else {
			log.warn("Login failure: " + emp.getUsername());
			ctx.status(401);
			ctx.html("Invalid login");
			return;
		}
		if (emp != null) {
			ctx.sessionAttribute("loggedEmployee", emp);
			ctx.json(emp);
			return;
		}
	}

	@Override
	public void logout(Context ctx) {
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}

	@Override
	public void register(Context ctx) {
		Employee emp = ctx.bodyAsClass(Employee.class);
		if (es.checkAvailability(emp.getUsername(), emp.getPassword())) {
			Employee newEmployee = es.register(emp.getUsername(), emp.getPassword(), emp.getEmail(), emp.getFirstName(),
					emp.getLastName(), emp.getDept());
			ns.notify(newEmployee, "Welcome!");
			ctx.status(201);
			ctx.json(newEmployee);
		} else {
			ctx.status(409);
			ctx.html("Username already taken");
		}
	}

	@Override
	public void checkNotifications(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String username = ctx.pathParam("username");
		if(loggedEmployee == null || !loggedEmployee.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		try {
			ctx.json(ns.checkNotifications(loggedEmployee));
		} catch (IllegalArgumentException i) {
			ctx.json("You've got no notifications!");
		}
	}
	
	public void viewEmployees(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee.getType().equals(EmployeeType.REGEMPLOYEE)) {
			ctx.status(403);
			log.warn("Unauthorized attempt to view all employees");
			return;
		}
		ctx.json(es.viewEmployees());
	}

	@Override
	public void viewRemainingReimbursement(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		ctx.json(1000 - loggedEmployee.getPendingReimbursement() - loggedEmployee.getAwardedReimbursement());
	}
}
