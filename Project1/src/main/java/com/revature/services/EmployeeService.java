package com.revature.services;

import java.util.List;

import com.revature.beans.Attachment;
import com.revature.beans.Employee;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;

public interface EmployeeService {

	Employee login(String name, String password);
	
	void logout();
	
	Employee register(String name, String password);
	
	boolean checkAvailability(String name);
	
	TuitionReimbursementForm createForm(String issuer, 
			String description, 
			Integer cost, 
			GradeType gradeType,
			ReimbursementEventType eventType,
			List<Attachment> attachments);
	
	void approveReimbursement(String employee);
	
	void declineReimbursement(String employee);
	
	void provideGrade(GradeType gradeType, String gradeValue);
	
}
