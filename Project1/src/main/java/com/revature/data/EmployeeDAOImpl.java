package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.util.CassandraUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addEmployee(Employee emp) {
		String query = "Insert into employee (username, password, type, firstname, lastname, reimbursement) values (?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(emp.getUsername(), emp.getPassword(), emp.getType().toString(),
				emp.getFirstName(), emp.getLastName(), emp.getReimbursement());
		session.execute(bound);
	}

	@Override
	public List<Employee> getEmployees() {
		String query = "Select username, password, type, firstname, lastname, reimbursement from employee";
		// This query will not be particularly efficient as it needs to query every
		// partition.
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<Employee> employees = new ArrayList<>();
		rs.forEach(row -> {
			Employee emp = new Employee();
			emp.setUsername(row.getString("username"));
			emp.setType(EmployeeType.valueOf(row.getString("type")));
			emp.setFirstName(row.getString("firstname"));
			emp.setLastName(row.getString("lastname"));
			emp.setReimbursement(row.getInt("reimbursement"));
			emp.setPassword(row.getString("password"));
			employees.add(emp);
		});
		return employees;
	}

	@Override
	public Employee getEmployeeByName(String username) {
		String query = "Select username, password, type, firstname, lastname, reimbursement from employee where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		// ResultSet is the values returned by my query.
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if (row == null) {
			return null;
		}
		Employee emp = new Employee();
		emp.setUsername(row.getString("username"));
		emp.setPassword(row.getString("password"));
		emp.setType(EmployeeType.valueOf(row.getString("type")));
		emp.setFirstName(row.getString("firstname"));
		emp.setLastName(row.getString("lastname"));
		emp.setReimbursement(row.getInt("reimbursement"));
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

	@Override
	public void updateEmployee(Employee e) {
		String query = "Update employee set type = ?, firstname = ?, lastname = ?, reimbursement = ? where username = ?;";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(e.getType().toString(), e.getFirstName(), e.getLastName(), e.getReimbursement());
		session.execute(bound);
	}
}
