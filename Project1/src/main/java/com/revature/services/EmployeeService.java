package com.revature.services;

import java.util.List;

import com.revature.beans.Attachment;
import com.revature.beans.Employee;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;

public interface EmployeeService {

	/**
	 * Logs an employee into the system
	 * @param name The username of the employee
	 * @param password The password of the employee
	 * @return The employee matching the input paramters 
	 */
	
	Employee login(String name, String password);
	
	/**
	 * The currently logged in employee is logged out of the system
	 */
	
	void logout();
	
	/**
	 * Registers a new employee with the system
	 * @param firstName The first name of the employee
	 * @param lastName The last name of the employee 
	 * @param username The username of the employee  
	 * @param password The password of the employee 
	 * @return The new employee that was created
	 */
	
	Employee register(String firstName, String lastName, String username, String password);
	
	/**
	 * Checks the availability of a username
	 * @param username The username whose availablity we want to check
	 * @return Whether the username is in the system already or not
	 */
	
	boolean checkAvailability(String username);
	
	/**
	 * Creates a Tuition Reimbursement Form 
	 * @param issuer The username of the employee who wants the form
	 * @param description The description of the event for which the form is being created for
	 * @param cost The cost of the event
	 * @param gradeType The type of grading scale being used
	 * @param eventType The type of event that the form is being used for
	 * @param attachments A list of relevant attachments for the form
	 * @return The created Tuition Form.
	 */
	
	TuitionReimbursementForm createForm(String issuer, 
			String description, 
			Integer cost, 
			GradeType gradeType,
			ReimbursementEventType eventType,
			List<Attachment> attachments);
	
	/**
	 * Approves a Tuition Reimbursement Form
	 * @param employee The employee whose form is to be approved
	 * @param id The id of the form (inside the list of forms from the same employee) to be approved
	 */
	
	void approveReimbursement(String employee, Integer id);
	
	/**
	 * Declines approval for a Tuition Reimbursement Form 
	 * @param employee The employee whose form is to be declined
	 * @param id The id of the form to be declined
	 */
	
	void declineReimbursement(String employee, Integer id);
	
	/**
	 * Allows an employee to provide a grade upon completion of the event for which they are requesting reimbursement
	 * @param gradeType
	 * @param gradeValue
	 */
	
	void provideGrade(GradeType gradeType, String gradeValue);
	
}
