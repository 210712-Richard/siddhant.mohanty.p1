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
	 * @param employee The updated version of the employee to be placed into the database
	 */

	void updateEmployee(Employee employee);
}
