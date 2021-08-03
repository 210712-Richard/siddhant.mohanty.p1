package com.revature.data;

import java.util.List;

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
	
}
