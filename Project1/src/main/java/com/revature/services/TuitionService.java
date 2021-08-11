package com.revature.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.revature.beans.Attachment;
import com.revature.beans.Employee;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;

public interface TuitionService {
	
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
