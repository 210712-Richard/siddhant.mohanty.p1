package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.EmployeeController;
import com.revature.controllers.EmployeeControllerImpl;
import com.revature.controllers.TuitionController;
import com.revature.controllers.TuitionControllerImpl;
import com.revature.factory.BeanFactory;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		instantiateDatabase();
		// launchJavalin();
	}

	public static void instantiateDatabase() {
		DataBaseCreator.dropTables();
		System.out.println("Tables dropped");
		try {
			Thread.sleep(40000); // wait 40 seconds
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.createTables();
		System.out.println("Tables created");
		try {
			Thread.sleep(50000); // wait 50 seconds
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataBaseCreator.populateEmployeeTable();
		DataBaseCreator.populateFormTable();
		DataBaseCreator.populateNotificationsTable();
		DataBaseCreator.populateDepartmentsTable();
		System.out.println("Tables populated");
		System.exit(0);
	}

	public static void launchjavalin() {
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);

		Javalin app = Javalin.create().start(8080);

		EmployeeController ec = (EmployeeController) BeanFactory.getFactory().get(EmployeeController.class,
				EmployeeControllerImpl.class);
		TuitionController tc = (TuitionController) BeanFactory.getFactory().get(TuitionController.class,
				TuitionControllerImpl.class); // TODO make tuition controller and add here.

		// login
		app.post("/employees", ec::login);

		// logout
		app.delete("/employees", ec::logout);

		// register
		app.put("/employees/register", ec::register);

		// check notifications
		app.get("/employees/:username/notifications", ec::checkNotifications);

		// create form
		app.post("/employees/newform", tc::createForm);

		// update form
		app.put("/employees/updateform", tc::updateForm);

		// approve form
		app.put("employees/administration/approval/:formissuer/:id", tc::approveReimbursement);

		// decline form
		app.put("employees/administration/declination/:formissuer/:id", tc::declineReimbursement);
		
		// view all forms in the entire system
		app.get("employees/administration/forms", tc::viewAllForms);
		
		// view all forms of the currently logged in employee
		app.get("employees/forms", tc::viewMyForms);
		
		// provide a grade for a form
		app.put("employees/review/:type/:grade", tc::provideGrade);
		
	}
}
