package com.revature.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import com.revature.beans.Employee;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.data.DepartmentDAO;
import com.revature.data.DepartmentDAOImpl;
import com.revature.data.EmployeeDAO;
import com.revature.data.EmployeeDAOImpl;
import com.revature.data.TuitionDAO;
import com.revature.data.TuitionDAOImpl;
import com.revature.factory.Log;

@Log
public class TuitionServiceImpl implements TuitionService {
	
	public EmployeeDAO ed = new EmployeeDAOImpl();
	public TuitionDAO td = new TuitionDAOImpl();
	public DepartmentDAO dd = new DepartmentDAOImpl();
	public EmployeeService es = new EmployeeServiceImpl();
	
	@Override
	public void createForm(UUID id, String issuer, String title, String description, String location, Double cost,
			LocalDate startDate, GradeType gradeType, ReimbursementEventType eventType, List<String> attachmentURIs) {
		TuitionReimbursementForm form = new TuitionReimbursementForm(id, issuer, title, description, location, cost,
				startDate, gradeType, eventType, attachmentURIs);
		LocalDate urgencyPeriod = LocalDate.now().plus(Period.of(0, 0, 14));
		if (startDate.isBefore(urgencyPeriod)) {
			form.setUrgent(true);
		}
		Employee employee = es.viewEmployee(issuer);
		employee.setPendingReimbursement(cost);
		ed.updateEmployee(employee);
		td.addTuitionForm(form);
	}
	
	public void deleteForm(TuitionReimbursementForm form) {
		Employee employee = es.viewEmployee(form.getIssuer());
		Double currentPending = employee.getPendingReimbursement();
		employee.setPendingReimbursement(currentPending - form.getCost());
		ed.updateEmployee(employee);
		td.deleteTuitionForm(form);
	}

	@Override
	public void approveReimbursement(Employee approver, String employee, UUID id) {
		TuitionReimbursementForm form = td.getTuitionForm(employee, id);
		Employee emp = ed.getEmployees().stream()
				.filter((e) -> e.getUsername().equals(employee))
				.findFirst()
				.orElse(null);
		switch (approver.getType()) {
		case REGEMPLOYEE:
			return; 
		case SUPERVISOR:
			if (approver.getIsDeptHead()) {
				if (emp.getSupervisorName().equals(approver.getUsername())) {
					form.setSupervisorApproved(true);
					form.setDeptHeadApproved(true);
					td.updateTuitionForm(form);
				} else if (dd.getDepartmentByName(emp.getDept()).getDeptHeadUsername().equals(approver.getUsername())) {
					form.setDeptHeadApproved(true);
				} else {
					return;
				}
			} else if (emp.getSupervisorName().equals(approver.getUsername())){
				form.setSupervisorApproved(true);
				td.updateTuitionForm(form);
			} else {
				return;
			}
			break;
		case BENCO:
			form.setBenCoApproved(true);
			td.updateTuitionForm(form);
			break;
		}
	}

	@Override
	public void declineReimbursement(Employee decliner, String employee, UUID id, String reason) {
		TuitionReimbursementForm form = td.getTuitionForm(employee, id);
		Employee emp = ed.getEmployees().stream()
				.filter((e) -> e.getUsername().equals(employee))
				.findFirst()
				.orElse(null);
		switch (decliner.getType()) {
		case REGEMPLOYEE:
			return; 
		case SUPERVISOR:
			if (decliner.getIsDeptHead()) {
				if (emp.getSupervisorName().equals(decliner.getUsername())) {
					form.setDeclined(true);
					td.updateTuitionForm(form);
				} else if (dd.getDepartmentByName(emp.getDept()).getDeptHeadUsername().equals(decliner.getUsername())) {
					form.setDeclined(true);
					td.updateTuitionForm(form);
				} else {
					return;
				}
			} else if (emp.getSupervisorName().equals(decliner.getUsername())){
				form.setDeclined(true);
				td.updateTuitionForm(form);
			} else {
				return;
			}
			break;
		case BENCO:
			form.setDeclined(true);
			td.updateTuitionForm(form);
			break;
		}
	}

	@Override
	public void provideGrade(String employee, UUID id, GradeType gradeType, String gradeValue) {
		TuitionReimbursementForm form = td.getTuitionForm(employee, id);
		form.setGradeType(gradeType);
		form.setGrade(gradeValue);
	}

	@Override
	public void updateForm(TuitionReimbursementForm form) {
		td.updateTuitionForm(form);
	}

	@Override
	public void autoApprove() {
		for (TuitionReimbursementForm form : td.getTuitionForms()) {
			if (form.getSupervisorApproved().equals(false)) {
				form.setSupervisorApproved(true);
				td.updateTuitionForm(form);
				return;
			} else if (form.getSupervisorApproved().equals(true) && form.getDeptHeadApproved().equals(false)) {
				form.setDeptHeadApproved(true);
				td.updateTuitionForm(form);
				return;
			}
		}
	}

	@Override
	public TuitionReimbursementForm getForm(Employee issuer, UUID id) {
		return td.getTuitionForm(issuer.getUsername(), id);
	}

	@Override
	public List<TuitionReimbursementForm> getForms() {
		return td.getTuitionForms();
	}

	@Override
	public List<TuitionReimbursementForm> getMyForms(Employee issuer) {
		return td.getTuitionFormsByEmployee(issuer.getUsername());
	}
}
