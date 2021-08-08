package com.revature;

import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.data.EmployeeDAO;
import com.revature.data.EmployeeDAOImpl;
import com.revature.data.NotificationDAO;
import com.revature.data.NotificationDAOImpl;
import com.revature.data.TuitionDAO;
import com.revature.data.TuitionDAOImpl;
import com.revature.util.CassandraUtil;

public class DataBaseCreator {

	private static EmployeeDAO ed = new EmployeeDAOImpl();
	private static TuitionDAO td = new TuitionDAOImpl();
	private static NotificationDAO nd = new NotificationDAOImpl();

	public static void dropTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS employee;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("DROP TABLE IF EXISTS form;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS notifications;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS departments;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}

	public static void createTables() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS employee (")
				.append("username text, password text, email text, firstname text, lastname text, ")
				.append("supervisorname text, dept text, pendingreimbursement double, ")
				.append("awardedreimbursement double, type text, forms list<UUID>, reviewforms list<UUID>, ")
				.append("primary key(username, firstname));");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS form (")
				.append("issuer text, description text, cost int, ")
				.append("gradetype text, eventtype text, attachments list<uuid>, creationdate date, creationtime time, primary key (issuer, description)); ");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS notifications (")
				.append("recipient text, message text, primary key (recipient)); ");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS departments (")
				.append("name text, deptheadusername text, primary key (name));");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
	}

	public static void populateEmployeeTable() {
		ed.addEmployee(new Employee("boss_man", "boss_password", "boss@man.com", "Boss", "Man", null, "Company", EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("sidd_mohanty", "sidd_password", "sidd@mohanty.com", "Sidd", "Mohanty", "ent", "Development", EmployeeType.REGEMPLOYEE));
		ed.addEmployee(new Employee("ham_yam", "ham_password", "ham@yam.com", "Ham", "Yam", "ent", "Human Resources", EmployeeType.REGEMPLOYEE));
		ed.addEmployee(new Employee("ent", "ed_password", "edward@trent.com", "Edward", "Trent", "boss_man", "Development", EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("eieio", "farm", "email.com", "Old", "McDonald", "boss_man", "Human Resources", EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("sponge", "bob_password", "bikini@bottom.com", "Robert", "Pants", "boss_man", "Benefits Coordination",  EmployeeType.BENCO));
	}

	public static void populateFormTable() {
		TuitionReimbursementForm form = new TuitionReimbursementForm("boss_man", "The first form",
				100, GradeType.LETTER, ReimbursementEventType.CERT, null);
		td.addTuitionForm(form);
	}
	
	public static void populateNotificationsTable() {
		nd.addNotification("boss_man", "You did it!");
	}
}
