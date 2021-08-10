package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.util.CassandraUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addEmployee(Employee emp) {
		String query = "INSERT INTO employee (username, password, email, firstname, lastname, "
				+ "supervisorname, dept, isdepthead, pendingreimbursement, "
				+ "awardedreimbursement, type, forms, reviewforms) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(emp.getUsername(), emp.getPassword(), emp.getEmail(),
				emp.getFirstName(), emp.getLastName(), emp.getSupervisorName(), emp.getDept(), emp.getIsDeptHead(),
				emp.getPendingReimbursement(), emp.getAwardedReimbursement(), emp.getType().toString(), emp.getForms(),
				emp.getReviewForms());
		session.execute(bound);
	}

	@Override
	public List<Employee> getEmployees() {
		String query = "SELECT username, password, email, firstname, lastname, "
				+ "supervisorname, dept, isdepthead, pendingreimbursement, "
				+ "awardedreimbursement, type, forms, reviewforms from employee;";
		// This query will not be particularly efficient as it needs to query every
		// partition.
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<Employee> employees = new ArrayList<>();
		rs.forEach(row -> {
			Employee emp = new Employee();
			emp.setUsername(row.getString("username"));
			emp.setPassword(row.getString("password"));
			emp.setFirstName(row.getString("firstname"));
			emp.setLastName(row.getString("lastname"));
			emp.setSupervisorName(row.getString("supervisorname"));
			emp.setDept(row.getString("dept"));
			emp.setIsDeptHead(row.getBoolean("isdepthead"));
			emp.setPendingReimbursement(row.getDouble("pendingreimbursement"));
			emp.setAwardedReimbursement(row.getDouble("awardedreimbursement"));
			emp.setType(EmployeeType.valueOf(row.getString("type")));
			emp.setForms(row.getList("forms", UUID.class));
			emp.setReviewForms(row.getList("reviewforms", UUID.class));
			employees.add(emp);
		});
		return employees;
	}

	@Override
	public Employee getEmployeeByName(String username, String password) {
		String query = "SELECT username, password, email, firstname, lastname, "
				+ "supervisorname, dept, isdepthead, pendingreimbursement, "
				+ "awardedreimbursement, type, forms, reviewforms FROM employee " + "WHERE username=? AND password=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username, password);
		// ResultSet is the values returned by my query.
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if (row == null) {
			return null;
		}
		Employee emp = new Employee();
		emp.setUsername(row.getString("username"));
		emp.setPassword(row.getString("password"));
		emp.setFirstName(row.getString("firstname"));
		emp.setLastName(row.getString("lastname"));
		emp.setSupervisorName(row.getString("supervisorname"));
		emp.setDept(row.getString("dept"));
		emp.setIsDeptHead(row.getBoolean("isdepthead"));;
		emp.setPendingReimbursement(row.getDouble("pendingreimbursement"));
		emp.setAwardedReimbursement(row.getDouble("awardedreimbursement"));
		emp.setType(EmployeeType.valueOf(row.getString("type")));
		emp.setForms(row.getList("forms", UUID.class));
		emp.setReviewForms(row.getList("reviewforms", UUID.class));
		return emp;
	}

	@Override
	public List<Employee> getEmployeeByType(EmployeeType type) {
		List<Employee> typedEmployees = new ArrayList<Employee>();
		List<Employee> employees = getEmployees();
		for (Employee e : employees) {
			if (e.getType().equals(type)) {
				typedEmployees.add(e);
			}
		}
		return typedEmployees;
	}
	
	public List<Employee> getEmployeeByDepartment(String department) {
		List<Employee> departmentEmployees = new ArrayList<Employee>();
		List<Employee> employees = getEmployees();
		for (Employee e : employees) {
			if (e.getDept().equals(department)) {
				departmentEmployees.add(e);
			}
		}
		return departmentEmployees;
	}

	@Override
	public void updateEmployee(Employee e) {
		String query = "UPDATE employee SET email=?, firstname=?, lastname=?, "
				+ "supervisorname=?, dept=?, isdepthead=?, pendingreimbursement=?, "
				+ "awardedreimbursement=?, type=?, forms=?, reviewforms=? " + "WHERE username=? AND password=?;";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(e.getEmail(), e.getFirstName(), e.getLastName(),
				e.getSupervisorName(), e.getDept(), e.getIsDeptHead(), e.getPendingReimbursement(), e.getAwardedReimbursement(),
				e.getType(), e.getForms(), e.getReviewForms(), e.getUsername(), e.getPassword());
		session.execute(bound);
	}
}
