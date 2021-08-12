package com.revature.controllers;

import io.javalin.http.Context;

public interface TuitionController {

	void createForm(Context ctx);
	
	// General purpose update for the sake of changing descriptions, titles, etc
	void updateForm(Context ctx);
	
	void approveReimbursement(Context ctx);
	
	void declineReimbursement(Context ctx);
	
	void provideGrade(Context ctx);

	void viewAllForms(Context ctx);
	
	void viewMyForms(Context ctx);
	
	void requestInformation(Context ctx);
	
	void addFile(Context ctx);
}
