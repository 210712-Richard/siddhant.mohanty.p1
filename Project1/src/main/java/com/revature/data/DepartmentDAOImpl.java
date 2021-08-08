package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.Department;
import com.revature.util.CassandraUtil;

public class DepartmentDAOImpl implements DepartmentDAO {

	CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addDepartment(Department department) {
		String query = "INSERT INTO departments (name, deptheadusername) VALUES (?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(department.getName(), department.getDeptHeadUsername());
		session.execute(bound);
	}

	@Override
	public List<Department> getDepartments() {
		String query = "SELECT name, deptheadusername FROM departments";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		ResultSet rs = session.execute(s);
		List<Department> departments = new ArrayList<Department>();
		rs.forEach(row -> {
			Department department = new Department();
			department.setName(row.getString("name"));
			department.setDeptHeadUsername(row.getString("deptheadusername"));
			departments.add(department);
		});
		return departments;
	}

	@Override
	public Department getDepartmentByName(String name) {
		List<Department> departments = getDepartments();
		Department nameDepartment = new Department();
		for (Department d : departments) {
			if (d.getName().equals(name)) {
				nameDepartment = d;
			}
		}
		return nameDepartment;
	}
}
