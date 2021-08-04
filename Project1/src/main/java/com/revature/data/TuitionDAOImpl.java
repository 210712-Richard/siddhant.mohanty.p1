package com.revature.data;

import java.time.LocalDateTime;
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
		String query = "Insert into form (issuer, creationtime, description, cost, gradetype, eventtype, attachments) values (?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(form.getIssuer(), form.getCreationTime(), form.getDescription(),
				form.getCost(), form.getGradeType().toString(), form.getEventType().toString(), form.getAttachments());
		session.execute(bound);
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionForms() {
		String query = "Select issuer, creationtime, description, cost, gradetype, eventtype, attachments from form";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<TuitionReimbursementForm> forms = new ArrayList<>();
		rs.forEach(row -> {
			TuitionReimbursementForm form = new TuitionReimbursementForm();
			form.setIssuer(row.getString("issuer"));
			form.setCreationTime(LocalDateTime.parse(row.getString("creationtime")));
			form.setDescription(row.getString("description"));
			form.setCost(row.getInt("cost"));
			form.setGradeType(GradeType.valueOf(row.getString("gradetype")));
			form.setEventType(ReimbursementEventType.valueOf(row.getString("eventtype")));
			form.setAttachments(row.getList("attachments", Attachment.class));
			forms.add(form);
		});
		return forms;
	}
}
