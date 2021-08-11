package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		String query = "INSERT INTO form (id, issuer, title, description, "
				+ "location, cost, startdate, creationdate, creationtime, "
				+ "gradetype, eventtype, attachments, urgent, "
				+ "supervisorapproved, deptheadapproved, bencoapproved, declined, "
				+ "reasondeclined, grade, passed, awardedamount, awardedreason, finalcheck) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)
				.build();
		BoundStatement bound = session.prepare(s).bind(form.getId(), form.getIssuer(), form.getTitle(),
				form.getDescription(), form.getLocation(), form.getCost(), form.getStartDate(), form.getCreationDate(),
				form.getCreationTime(), form.getGradeType().toString(), form.getEventType().toString(),
				form.getAttachments(), form.getUrgent(), form.getSupervisorApproved(), form.getDeptHeadApproved(),
				form.getBenCoApproved(), form.getDeclined(), form.getReasonDeclined(), form.getGrade(),
				form.getPassed(), form.getAwardedAmount(), form.getAwardedReason(), form.getFinalCheck());
		session.execute(bound);
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionForms() {
		String query = "SELECT id, issuer, title, description, "
				+ "location, cost, startdate, creationdate, creationtime, "
				+ "gradetype, eventtype, attachments, urgent, "
				+ "supervisorapproved, deptheadapproved, bencoapproved, declined, "
				+ "reasondeclined, grade, passed, awardedamount, awardedreason, finalcheck FROM form";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<TuitionReimbursementForm> forms = new ArrayList<TuitionReimbursementForm>();
		rs.forEach(row -> {
			TuitionReimbursementForm form = new TuitionReimbursementForm();
			form.setId(row.getUuid("id"));
			form.setIssuer(row.getString("issuer"));
			form.setTitle(row.getString("title"));
			form.setDescription(row.getString("description"));
			form.setLocation(row.getString("location"));
			form.setCost(row.getDouble("cost"));
			form.setStartDate(row.getLocalDate("startdate"));
			form.setCreationDate(row.getLocalDate("creationdate"));
			form.setCreationTime(row.getLocalTime("creationtime"));
			form.setGradeType(GradeType.valueOf(row.getString("gradetype")));
			form.setEventType(ReimbursementEventType.valueOf(row.getString("eventtype")));
			form.setAttachments(row.getList("attachments", Attachment.class));
			form.setUrgent(row.getBoolean("urgent"));
			form.setSupervisorApproved(row.getBoolean("supervisorapproved"));
			form.setDeptHeadApproved(row.getBoolean("deptheadapproved"));
			form.setBenCoApproved(row.getBoolean("bencoapproved"));
			form.setDeclined(row.getBoolean("declined"));
			form.setReasonDeclined(row.getString("reasondeclined"));
			form.setGrade(row.getString("grade"));
			form.setPassed(row.getBoolean("passed"));
			form.setAwardedAmount(row.getDouble("awardedamount"));
			form.setAwardedReason(row.getString("awardedreason"));
			form.setFinalCheck(row.getBoolean("finalcheck"));
			forms.add(form);
		});
		return forms;
	}

	@Override
	public List<TuitionReimbursementForm> getTuitionFormsByEmployee(String issuer) {
		String query = "SELECT id, issuer, title, description, "
				+ "location, cost, startdate, creationdate, creationtime, "
				+ "gradetype, eventtype, attachments, urgent, "
				+ "supervisorapproved, deptheadapproved, bencoapproved, declined, "
				+ "reasondeclined, grade, passed, awardedamount, awardedreason, finalcheck FROM form WHERE issuer=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(issuer);
		ResultSet rs = session.execute(bound);
		List<TuitionReimbursementForm> forms = new ArrayList<TuitionReimbursementForm>();
		rs.forEach(row -> {
			TuitionReimbursementForm form = new TuitionReimbursementForm();
			form.setId(row.getUuid("id"));
			form.setIssuer(row.getString("issuer"));
			form.setTitle(row.getString("title"));
			form.setDescription(row.getString("description"));
			form.setLocation(row.getString("location"));
			form.setCost(row.getDouble("cost"));
			form.setStartDate(row.getLocalDate("startdate"));
			form.setCreationDate(row.getLocalDate("creationdate"));
			form.setCreationTime(row.getLocalTime("creationtime"));
			form.setGradeType(GradeType.valueOf(row.getString("gradetype")));
			form.setEventType(ReimbursementEventType.valueOf(row.getString("eventtype")));
			form.setAttachments(row.getList("attachments", Attachment.class));
			form.setUrgent(row.getBoolean("urgent"));
			form.setSupervisorApproved(row.getBoolean("supervisorapproved"));
			form.setDeptHeadApproved(row.getBoolean("deptheadapproved"));
			form.setBenCoApproved(row.getBoolean("bencoapproved"));
			form.setDeclined(row.getBoolean("declined"));
			form.setReasonDeclined(row.getString("reasondeclined"));
			form.setGrade(row.getString("grade"));
			form.setPassed(row.getBoolean("passed"));
			form.setAwardedAmount(row.getDouble("awardedamount"));
			form.setAwardedReason(row.getString("awardedreason"));
			form.setFinalCheck(row.getBoolean("finalcheck"));
			forms.add(form);
		});
		return forms;
	}
	
	@Override
	public TuitionReimbursementForm getTuitionForm(String issuer, UUID id) {
		List<TuitionReimbursementForm> forms = getTuitionFormsByEmployee(issuer);
		TuitionReimbursementForm form = forms.stream()
				.filter((f) -> f.getId().equals(id))
				.findFirst()
				.orElse(null);
		return form;
	}

	@Override
	public void updateTuitionForm(TuitionReimbursementForm form) {
		String query = "UPDATE form SET title=?, description=?, location=?, "
				+ "cost=?, startdate=?, creationdate=?, creationtime=?, "
				+ "gradetype=?, eventtype=?, attachments=?, urgent=?, "
				+ "supervisorapproved=?, deptheadapproved=?, bencoapproved=?, "
				+ "declined=?, reasondeclined=?, grade=?, passed=?, awardedamount=?, "
				+ "awardedreason=?, finalcheck=? WHERE issuer=? AND id=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(form.getTitle(), form.getDescription(), form.getLocation(),
				form.getCost(), form.getStartDate(), form.getCreationDate(), form.getCreationTime(),
				form.getGradeType().toString(), form.getEventType().toString(), form.getAttachments(), form.getUrgent(),
				form.getSupervisorApproved(), form.getDeptHeadApproved(), form.getBenCoApproved(), form.getDeclined(),
				form.getReasonDeclined(), form.getGrade(), form.getPassed(), form.getAwardedAmount(),
				form.getAwardedReason(), form.getFinalCheck(), form.getIssuer(), form.getId());
		session.execute(bound);
	}
}
