package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;

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
	 * Gets an Employee object
	 * 
	 * @param username username of employee
	 * @return the employee
	 */

	Employee viewEmployee(String username);
	
	/**
	 * Gets the entire list of Employees
	 * 
	 * @return the entire list of employees
	 */
	
	List<Employee> viewEmployees();

}
