package com.revature.data;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.TuitionReimbursementForm;

public interface EmployeeDAO {

	/**
	 * Adds an employee to the list of employees in the system
	 * 
	 * @param employee The employee being added
	 */

	void addEmployee(Employee employee);

	/**
	 * Gets the entire list of employees from the system
	 * 
	 * @return the list of employees
	 */

	List<Employee> getEmployees();

	/**
	 * Gets a particular employee from the system
	 * 
	 * @param username The username of the employee
	 * @return The employee whose username matches the input parameter
	 */

	Employee getEmployeeByName(String username);

	/**
	 * Gets all the employees of a particular type
	 * 
	 * @param type The type of employee we want
	 * @return A list of all employees whose type matches the input parameter
	 */

	List<Employee> getEmployeeByType(EmployeeType type);

	/**
	 * Updates an employee within the system
	 * 
	 * @param username The username of the employee to be updated
	 */

	void updateEmployee(String username);

	/**
	 * Gets all tuition reimbursement forms submitted by a particular employee
	 * 
	 * @param username The username of the employee
	 * @return The forms belonging to that employee
	 */

	List<TuitionReimbursementForm> getEmployeeForms(String username);

	/**
	 * Gets all notifications for a particular employee
	 * 
	 * @param username the username of the employee
	 * @return The list of notifications that employee has pending
	 */

	List<String> getEmployeeNotifications(String username);
}
