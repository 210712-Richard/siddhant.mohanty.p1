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
	}

	public static void populateEmployeeTable() {
		ed.addEmployee(new Employee("Boss", "Man", "boss_man", "boss_password", EmployeeType.CEO));
		ed.addEmployee(new Employee("Sidd", "Mohanty", "sidd_mohanty", "sidd_password", EmployeeType.REGEMPLOYEE));
		ed.addEmployee(new Employee("Ham", "Yam", "ham_yam", "ham_password", EmployeeType.MANAGER));
		ed.addEmployee(new Employee("Edward", "Trent", "ent", "ed_password", EmployeeType.DEPARTMENT_HEAD));
		ed.addEmployee(new Employee("Robert", "Pants", "sponge", "bob_password", EmployeeType.BENCO));
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
