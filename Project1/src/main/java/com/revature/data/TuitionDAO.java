package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.TuitionReimbursementForm;

public interface TuitionDAO {

	/**
	 * Adds a tuition reimbursement form to the system
	 * 
	 * @param form The form being added
	 */

	void addTuitionForm(TuitionReimbursementForm form);

	/**
	 * Gets the entire list of Tuition Reimbursement Forms
	 * 
	 * @return The list of forms
	 */

	List<TuitionReimbursementForm> getTuitionForms();

	/**
	 * Gets the list of forms submitted by a particular employee
	 * 
	 * @param issuer The username of the employee who issued the form
	 * @return The list of forms sumbitted by that employee
	 */

	List<TuitionReimbursementForm> getTuitionFormsByEmployee(String issuer);

	/**
	 * Gets a Tuition form
	 * 
	 * @param issuer the username of the form issuer
	 * @param id     The ID of the form
	 * @return the form
	 */

	TuitionReimbursementForm getTuitionForm(String issuer, UUID id);

	/**
	 * Updates a form that is already in the system with new values
	 * 
	 * @param form The form to be updated
	 */

	void updateTuitionForm(TuitionReimbursementForm form);
	
	void deleteTuitionForm(TuitionReimbursementForm form);

}
