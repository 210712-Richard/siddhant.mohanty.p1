package com.revature.data;

import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.Attachment;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.util.CassandraUtil;

public class TuitionDAOImpl implements TuitionDAO {

	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addTuitionForm(TuitionReimbursementForm form) {
		String query = "Insert into form (issuer, description, cost, gradetype, eventtype, attachments, creationdate, creationtime) values (?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(form.getIssuer(), form.getDescription(), form.getCost(),
				form.getGradeType().toString(), form.getEventType().toString(), form.getAttachments(), form.getCreationDate(), form.getCreationTime());
		session.execute(bound);
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionForms() {
		String query = "Select issuer, description, cost, gradetype, eventtype, attachments, creationdate, creationtime from form";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<TuitionReimbursementForm> forms = new ArrayList<TuitionReimbursementForm>();
		rs.forEach(row -> {
			TuitionReimbursementForm form = new TuitionReimbursementForm();
			form.setIssuer(row.getString("issuer"));
			form.setDescription(row.getString("description"));
			form.setCost(row.getInt("cost"));
			form.setGradeType(GradeType.valueOf(row.getString("gradetype")));
			form.setEventType(ReimbursementEventType.valueOf(row.getString("eventtype")));
			form.setAttachments(row.getList("attachments", Attachment.class));
			form.setCreationDate(row.getLocalDate("creationdate"));
			form.setCreationTime(row.getLocalTime("creationtime"));
			forms.add(form);
		});
		return forms;
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionFormsByEmployee(String issuer) {
		String query = "Select issuer, description, cost, gradetype, eventtype, attachments, creationdate, creationtime from form where issuer=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(issuer);
		ResultSet rs = session.execute(bound);
		List<TuitionReimbursementForm> forms = new ArrayList<TuitionReimbursementForm>();
		rs.forEach(row -> {
			TuitionReimbursementForm form = new TuitionReimbursementForm();
			form.setIssuer(row.getString("issuer"));
			form.setDescription(row.getString("description"));
			form.setCost(row.getInt("cost"));
			form.setGradeType(GradeType.valueOf(row.getString("gradetype")));
			form.setEventType(ReimbursementEventType.valueOf(row.getString("eventtype")));
			form.setAttachments(row.getList("attachments", Attachment.class));
			form.setCreationDate(row.getLocalDate("creationdate"));
			form.setCreationTime(row.getLocalTime("creationtime"));
			forms.add(form);
		});
		return forms;
	}
}
