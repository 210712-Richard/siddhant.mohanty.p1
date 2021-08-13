package com.revature.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
import com.revature.services.NotificationService;
import com.revature.services.NotificationServiceImpl;
import com.revature.services.TuitionService;
import com.revature.services.TuitionServiceImpl;
import com.revature.util.S3Util;

import io.javalin.http.Context;

@Log
public class TuitionControllerImpl implements TuitionController {

	private static final String[] ATTACHMENTTYPES = {".jpg", ".png", ".txt", ".doc", ".pdf"};
	private static final S3Util S3 = S3Util.getInstance();

	private Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
	TuitionService ts = (TuitionService) BeanFactory.getFactory().get(TuitionService.class, TuitionServiceImpl.class);
	EmployeeService es = (EmployeeService) BeanFactory.getFactory().get(EmployeeService.class,
			EmployeeServiceImpl.class);
	NotificationService ns = (NotificationService) BeanFactory.getFactory().get(NotificationService.class,
			NotificationServiceImpl.class);

	@Override
	public void createForm(Context ctx) {
		TuitionReimbursementForm form = ctx.bodyAsClass(TuitionReimbursementForm.class);
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		form.setIssuer(loggedEmployee.getUsername());
		List<String> attachmentURIs = new ArrayList<String>();
		ts.createForm(form.getId(), form.getIssuer(), form.getTitle(), form.getDescription(), form.getLocation(), form.getCost(),
				form.getStartDate(), form.getGradeType(), form.getEventType(), attachmentURIs);
		ns.notify(loggedEmployee, "You created a form with title: " + form.getTitle());
		log.trace("Form created: " + form.getTitle() + " by " + form.getIssuer());
		ctx.html(form.getTitle() + " form created");
		ctx.json(form);
	}

	@Override
	public void updateForm(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String issuer = ctx.pathParam("issuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		if (loggedEmployee == null || !loggedEmployee.getUsername().equals(issuer)) {
			ctx.status(403);
			return;
		}
		TuitionReimbursementForm newForm = ctx.bodyAsClass(TuitionReimbursementForm.class);
		ts.updateForm(newForm);
		ns.notify(loggedEmployee, "You updated the form with title: " + newForm.getTitle());
		log.trace("Form updated: " + newForm.getTitle() + " by " + newForm.getIssuer());
		ctx.html(newForm.getTitle() + " form updated");
		ctx.json(newForm);
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
		ns.notify(es.viewEmployee(issuer),
				"Your form with id: " + id + "\n was approved by " + loggedEmployee.getUsername());
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
		String reason = ctx.body();
		ts.declineReimbursement(loggedEmployee, issuer, id, reason);
		ns.notify(es.viewEmployee(issuer), "Your form with id: " + id + "\n was declineed by "
				+ loggedEmployee.getUsername() + " with reason: " + reason);
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
			log.warn("Unauthorized attempt to view all forms: " + loggedEmployee.getUsername());
			;
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
		ctx.json(ts.getMyForms(loggedEmployee));
	}

	@Override
	public void requestInformation(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String issuer = ctx.pathParam("issuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		String instructions = ctx.body();
		if (loggedEmployee == null) {
			ctx.status(403);
			return;
		}
		if (loggedEmployee.getType().equals(EmployeeType.REGEMPLOYEE)) {
			log.warn("Unauthorized attempt to request more information: " + loggedEmployee.getUsername());
			ctx.status(401);
			return;
		}
		ns.notify(es.viewEmployee(issuer), "The form you sumbitted with id " + id + " needs more information.\n"
				+ "The requester has provided the instructions: " + instructions);
	}

	@Override
	public void addAttachment(Context ctx) {
		Employee loggedEmployee = ctx.sessionAttribute("loggedEmployee");
		String attachmentType = ctx.header("attachmentType");
		String issuer = ctx.pathParam("issuer");
		UUID id = UUID.fromString(ctx.pathParam("id"));
		if (loggedEmployee == null || !loggedEmployee.getUsername().equals(issuer)) {
			ctx.status(403);
			return;
		}
		if (!Stream.of(ATTACHMENTTYPES).anyMatch((type) -> type.equals(attachmentType))) {
			log.warn("Bad attachment type supplied");
			ctx.status(400);
			ctx.html("Unsupported attachment type");
		}
		TuitionReimbursementForm form = ts.getForm(loggedEmployee, id);
		String attachmentURI = id + "/attachments/" + form.getAttachmentURIs().size() + attachmentType;
		S3.uploadToBucket(attachmentURI, ctx.bodyAsBytes());
		form.getAttachmentURIs().add(attachmentURI);
		ts.updateForm(form);
	}
}
