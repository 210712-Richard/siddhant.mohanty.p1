package com.revature.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.revature.beans.Attachment;
import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;

/**
 * Contains methods that allow employees to perform certain actions
 *
 */

public interface EmployeeService {

	/**
	 * Logs an employee into the system
	 * 
	 * @param username The username of the employee
	 * @param password The password of the employee
	 * @return The employee matching the input paramters
	 */

	Employee login(String username, String password);

	/**
	 * Checks if a user with the given credentials exists in the system
	 * 
	 * @param username The username supplied
	 * @param password The password supplied
	 * @return
	 */

	Boolean authenticate(String username, String password);

	/**
	 * Registers a new employee with the system. A supervisor is randomly selected
	 * within the department that the employee is registering under
	 * 
	 * @param firstName The first name of the employee
	 * @param lastName  The last name of the employee
	 * @param username  The username of the employee
	 * @param password  The password of the employee
	 * @param email     The email of the employee
	 * @param dept      The name of the department that the employee is joining
	 * @return The new employee that was created
	 */

	Employee register(String username, String password, String email, String firstName, String lastName, String dept);

	/**
	 * Checks the availability of a username
	 * 
	 * @param username The username whose availability we want to check
	 * @param password The supplied password
	 * @return Whether the username is in the system already or not
	 */

	boolean checkAvailability(String username, String password);

	/**
	 * Creates a tuition reimbursement form
	 * 
	 * @param issuer      The username of the employee issuing the form
	 * @param title       The title of the form
	 * @param description The description of the event that reimbursement is being
	 *                    requested for
	 * @param location    The location of the event
	 * @param cost        The cost of the event
	 * @param startDate   The starting date of the event
	 * @param gradeType   The type of grade that the event will be using
	 * @param eventType   The type of event
	 * @param attachments Any attachments
	 */

	void createForm(String issuer, String title, String description, String location, Double cost, LocalDate startDate,
			GradeType gradeType, ReimbursementEventType eventType, List<Attachment> attachments);

	/**
	 * Approves a Tuition Reimbursement Form
	 * 
	 * @param approver The employee who is doing the approval
	 * @param employee The name of the employee whose form is to be approved
	 * @param id       The UUID of the form (inside the list of forms from the same
	 *                 employee) to be approved
	 */

	void approveReimbursement(Employee approver, String employee, UUID id);

	/**
	 * Declines approval for a Tuition Reimbursement Form
	 * 
	 * @param employee The employee whose form is to be declined
	 * @param id       The UUID of the form to be declined
	 * @param reason   The reason the form was declined
	 */

	void declineReimbursement(String employee, UUID id, String reason);

	/**
	 * Allows an employee to provide a grade upon completion of the event for which
	 * they are requesting reimbursement
	 * 
	 * @param employee   Name of employee who is uploading grade
	 * @param id         ID of form being updated with grade
	 * @param gradeType  Type of grade being provided
	 * @param gradeValue Value of the grade
	 */

	void provideGrade(String employee, UUID id, GradeType gradeType, String gradeValue);

}
