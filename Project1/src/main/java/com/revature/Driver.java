package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.EmployeeController;
import com.revature.controllers.EmployeeControllerImpl;
import com.revature.controllers.TuitionController;
import com.revature.controllers.TuitionControllerImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.TuitionService;
import com.revature.services.TuitionServiceImpl;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

@Log
public class Driver {
	public static void main(String[] args) {
		//instantiateDatabase();
		doAutoApprovals();
		launchJavalin();
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
			Thread.sleep(50000); // wait 70 seconds
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

	public static void launchJavalin() {
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);

		Javalin app = Javalin.create().start(8080);

		EmployeeController ec = (EmployeeController) BeanFactory.getFactory().get(EmployeeController.class,
				EmployeeControllerImpl.class);
		TuitionController tc = (TuitionController) BeanFactory.getFactory().get(TuitionController.class,
				TuitionControllerImpl.class); 

		// login
		app.post("/employees", ec::login);

		// logout
		app.delete("/employees", ec::logout);

		// register
		app.put("/employees/register", ec::register);

		// check notifications
		app.get("/employees/:username/notifications", ec::checkNotifications);
		
		// check remaining reimbursement funds
		app.get("/employees/funds", ec::checkFunds);
		
		// look at all employees
		app.get("/employees/view", ec::viewEmployees);

		// create form
		app.post("/employees/newform", tc::createForm);

		// update form
		app.put("/employees/updateform/:issuer/:id", tc::updateForm);
		
		// delete form
		app.put("/employees/deleteform/:issuer/:id", tc::deleteForm);
		
		// add attachment to a form
		app.put("/employees/addattachment/:issuer/:id", tc::addAttachment);

		// approve form
		app.put("/employees/administration/approval/:formissuer/:id", tc::approveReimbursement);

		// decline form
		app.put("/employees/administration/declination/:formissuer/:id", tc::declineReimbursement);
		
		// view all forms in the entire system
		app.get("/employees/administration/forms/view", tc::viewAllForms);
		
		// view all forms of the currently logged in employee
		app.get("/employees/forms/view", tc::viewMyForms);
		
		// provide a grade for a form
		app.put("/employees/forms/:id/:type/:grade/:isPassing", tc::provideGrade);
		
		// ask for more information in a form
		app.put("/employees/administration/forms/:issuer/:id", tc::requestInformation);
	}
	
	public static void doAutoApprovals() {
		Runnable autoApproval = () -> {
			TuitionService ts = (TuitionService) BeanFactory.getFactory().get(TuitionService.class, TuitionServiceImpl.class);
		
			while(true) {
				try {
					// Wait 60 seconds to perform each stage of the check, 2 mins total for a 
					// form to fully auto approve itself (besides benco)
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ts.autoApprove();
			}
		};
		Thread thread = new Thread(autoApproval);
		thread.start();
	}
}
