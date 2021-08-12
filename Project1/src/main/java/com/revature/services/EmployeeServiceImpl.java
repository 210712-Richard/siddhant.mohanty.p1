package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.data.DepartmentDAO;
import com.revature.data.DepartmentDAOImpl;
import com.revature.data.EmployeeDAO;
import com.revature.data.EmployeeDAOImpl;


public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO ed = new EmployeeDAOImpl();
	private DepartmentDAO dd = new DepartmentDAOImpl();

	/**
	 * login() should only be called if the username and password match a user in
	 * the system. In project 0, checking whether the requested user actually exists
	 * was relegated to this method, but for the sake of more modularity, it should
	 * be separate.
	 */

	@Override
	public Employee login(String username, String password) {
		return ed.getEmployeeByName(username, password);
	}

	/**
	 * register() should only be called if the username does not already exist in
	 * the system, and if the supplied department name DOES exist already.
	 */

	@Override
	public Employee register(String username, String password, String email, String firstName, String lastName,
			String dept) {
		Random rand = new Random();
		List<Employee> typedDepartmentEmployees = new ArrayList<>();
		for (Employee emp : ed.getEmployeeByType(EmployeeType.SUPERVISOR)) {
			if (emp.getDept().equals(dept)) {
				typedDepartmentEmployees.add(emp);
			}
		}
		String randomSupervisorName = typedDepartmentEmployees.get(rand.nextInt(typedDepartmentEmployees.size()))
				.getUsername();
		Employee e = new Employee(username, password, email, firstName, lastName, randomSupervisorName, dept, false,
				EmployeeType.REGEMPLOYEE);
		ed.addEmployee(e);
		return e;
	}

	@Override
	public boolean checkAvailability(String username, String password) {
		List<Employee> employees = ed.getEmployees();
		Employee emp = employees.stream()
				.filter((e) -> e.getUsername().equals(username) && e.getPassword().equals(password))
				.findFirst()
				.orElse(null);
		return emp == null ? true : false;
	}

	@Override
	public Boolean authenticate(String username, String password) {
		Employee emp = ed.getEmployeeByName(username, password);
		return emp == null ? false : true;
	}

	@Override
	public Employee viewEmployee(String username) {
		return ed.getEmployees().stream()
				.filter((e) -> e.getUsername().equals(username))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Employee> viewEmployees() {
		return ed.getEmployees();
	}
}
