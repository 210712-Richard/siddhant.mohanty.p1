package com.revature.data;

import java.util.List;

import com.revature.beans.Department;

public interface DepartmentDAO {

	/**
	 * Adds a department to the system
	 * 
	 * @param department The department to be added
	 */
	
	void addDepartment(Department department);
	
	/**
	 * Gets all departments from the system
	 * 
	 * @return A list of all departments
	 */
	
	List<Department> getDepartments();
	
	/**
	 * Get a particular department by its name
	 * 
	 * @param name The name of the department
	 * @return The department object
	 */
	
	Department getDepartmentByName(String name);
}
