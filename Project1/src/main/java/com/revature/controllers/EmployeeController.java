package com.revature.controllers;

import io.javalin.http.Context;

public interface EmployeeController {

	void login(Context ctx);
	
	void logout(Context ctx);
	
	void register(Context ctx);
	
	void checkNotifications(Context ctx);
	
}
