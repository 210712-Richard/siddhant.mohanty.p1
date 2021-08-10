package com.revature.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Attachment;
import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.data.DepartmentDAO;
import com.revature.data.DepartmentDAOImpl;
import com.revature.data.EmployeeDAO;
import com.revature.data.EmployeeDAOImpl;
import com.revature.data.TuitionDAO;
import com.revature.data.TuitionDAOImpl;

public class EmployeeServiceImpl implements EmployeeService {
	
	private Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
	public EmployeeDAO ed = new EmployeeDAOImpl();
	public DepartmentDAO dd = new DepartmentDAOImpl();
	public TuitionDAO td = new TuitionDAOImpl();
	
	/**
	 * login() should only be called if the username and password match a user in the system.
	 * In project 0, checking whether the requested user actually exists was relegated to 
	 * this method, but for the sake of more modularity, it should be separate.
	 */
	
	@Override
	public Employee login(String username, String password) {
		 return ed.getEmployeeByName(username, password);
	}

	/**
	 * register() should only be called if the username does not already exist in the system,
	 * and if the supplied department name DOES exist already.
	 */
	
	@Override
	public Employee register(String username, String password, String email, String firstName, String lastName, String dept) {
		Random rand = new Random();
		List<Employee> typedDepartmentEmployees = new ArrayList<>();
		for (Employee emp : ed.getEmployeeByType(EmployeeType.SUPERVISOR)) {
			if (emp.getDept().equals(dept)) {
				typedDepartmentEmployees.add(emp);
			}
		}
		String randomSupervisorName = typedDepartmentEmployees.get(rand.nextInt(typedDepartmentEmployees.size())).getUsername(); 
		Employee e = new Employee(username, password, email, firstName, lastName, randomSupervisorName, dept, false, EmployeeType.REGEMPLOYEE);
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
		return emp==null ? true : false;
	}

	@Override
	public void createForm(String issuer, String title, String description, String location,
			Double cost, LocalDate startDate, GradeType gradeType, ReimbursementEventType eventType,
			List<Attachment> attachments) {
		TuitionReimbursementForm form = new TuitionReimbursementForm(issuer, title, description, location, cost, startDate, gradeType, eventType, attachments);
		td.addTuitionForm(form);
	}

	@Override
	public void approveReimbursement(Employee approver, String employee, UUID id) {
		List<TuitionReimbursementForm> forms = td.getTuitionFormsByEmployee(employee);
		TuitionReimbursementForm form = forms.stream()
				.filter((f) -> f.getId().equals(id))
				.findFirst()
				.orElse(null);
		switch(approver.getType()) {
		case REGEMPLOYEE:
			return;
		case SUPERVISOR:
			form.setSupervisorApproved(true);
			break;
		case BENCO:
			form.setBenCoApproved(true);
		}
	}

	@Override
	public void declineReimbursement(String employee, UUID id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void provideGrade(GradeType gradeType, String gradeValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean authenticate(String username, String password) {
		Employee emp = ed.getEmployeeByName(username, password);
		return emp==null ? false : true;
	}

}
