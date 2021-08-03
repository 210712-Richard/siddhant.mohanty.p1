package com.revature.data;

import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.util.CassandraUtil;

public class TuitionDAOImpl implements TuitionDAO{

	private CqlSession session = CassandraUtil.getInstance().getSession();
	
	@Override
	public void addTuitionForm(TuitionReimbursementForm form) {
		String query = "Insert into form (issuer, creationtime, description, cost, gradetype, eventtype, attachments) values (?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(form.getIssuer(), form.getCreationTime(), form.getDescription(), emp.getType().toString(), emp.getReimbursement());
		session.execute(bound);
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionForms() {
		// TODO Auto-generated method stub
		return null;
	}

}
