package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.GradeType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.factory.BeanFactory;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.TuitionService;
import com.revature.services.TuitionServiceImpl;

import io.javalin.http.Context;

public class TuitionControllerImpl implements TuitionController {

	private Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
	TuitionService ts = (TuitionService) BeanFactory.getFactory().get(TuitionService.class, TuitionServiceImpl.class);
	EmployeeService es = (EmployeeService) BeanFactory.getFactory().get(EmployeeService.class,
			EmployeeServiceImpl.class);

	@Override
	public void createForm(Context ctx) {
		TuitionReimbursementForm form = ctx.bodyAsClass(TuitionReimbursementForm.class);
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null || !loggedEmployee.getUsername().equals(form.getIssuer())) {
			ctx.status(403);
			return;
		}
		ts.createForm(form.getIssuer(), form.getTitle(), form.getDescription(), form.getLocation(), form.getCost(),
				form.getStartDate(), form.getGradeType(), form.getEventType(), form.getAttachments());
		log.trace("Form created: " + form.getTitle() + " by " + form.getIssuer());
		ctx.html(form.getTitle() + " form created");
	}

	@Override
	public void updateForm(Context ctx) {
		TuitionReimbursementForm form = ctx.bodyAsClass(TuitionReimbursementForm.class);
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null || !loggedEmployee.getUsername().equals(form.getIssuer())) {
			ctx.status(403);
			return;
		}
		ts.updateForm(form.getIssuer(), form.getTitle(), form.getDescription(), form.getLocation(), form.getCost(),
				form.getStartDate(), form.getGradeType(), form.getEventType(), form.getAttachments());
		log.trace("Form updated: " + form.getTitle() + " by " + form.getIssuer());
		ctx.html(form.getTitle() + " form updated");
	}

	@Override
	public void approveReimbursement(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String issuer = ctx.pathParam("formissuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		ts.approveReimbursement(loggedEmployee, issuer, id);
	}

	@Override
	public void declineReimbursement(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String issuer = ctx.pathParam("formissuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		String reason = ctx.bodyAsClass(String.class);
		ts.declineReimbursement(loggedEmployee, issuer, id, reason);
	}

	@Override
	public void provideGrade(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		GradeType type = GradeType.valueOf(ctx.pathParam("type"));
		String grade = ctx.pathParam("grade");
		ts.provideGrade(loggedEmployee.getUsername(), id, type, grade);
	}

	@Override
	public void autoApprove(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewAllForms(Context ctx) {
		ts.getForms();
	}

	@Override
	public void viewMyForms(Context ctx) {
		// TODO Auto-generated method stub

	}

}
