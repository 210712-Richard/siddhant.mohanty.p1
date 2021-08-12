package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.GradeType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.TuitionService;
import com.revature.services.TuitionServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;

@Log
public class TuitionControllerImpl implements TuitionController {

	private static final S3Util S3 = S3Util.getInstance();
	
	
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
				form.getStartDate(), form.getGradeType(), form.getEventType(), form.getAttachmentURIs());
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
				form.getStartDate(), form.getGradeType(), form.getEventType(), form.getAttachmentURIs());
		log.trace("Form updated: " + form.getTitle() + " by " + form.getIssuer());
		ctx.html(form.getTitle() + " form updated");
	}

	@Override
	public void approveReimbursement(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		String issuer = ctx.pathParam("formissuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		ts.approveReimbursement(loggedEmployee, issuer, id);
	}

	@Override
	public void declineReimbursement(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		String issuer = ctx.pathParam("formissuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		String reason = ctx.bodyAsClass(String.class);
		ts.declineReimbursement(loggedEmployee, issuer, id, reason);
	}

	@Override
	public void provideGrade(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		UUID id = UUID.fromString(ctx.pathParam("id"));
		GradeType type = GradeType.valueOf(ctx.pathParam("type"));
		String grade = ctx.pathParam("grade");
		ts.provideGrade(loggedEmployee.getUsername(), id, type, grade);
	}

	@Override
	public void viewAllForms(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		if (loggedEmployee.getType().equals(EmployeeType.REGEMPLOYEE)) {
			log.warn("Unauthorized attempt to view all forms: " + loggedEmployee.getUsername());;
			ctx.status(401);
			return;
		}
		ctx.json(ts.getForms());
	}

	@Override
	public void viewMyForms(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		ts.getMyForms(loggedEmployee);
	}

}
