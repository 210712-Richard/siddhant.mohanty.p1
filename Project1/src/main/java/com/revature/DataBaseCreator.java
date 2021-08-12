package com.revature;

import java.time.LocalDate;

import com.revature.beans.Department;
import com.revature.beans.Employee;
import com.revature.beans.EmployeeType;
import com.revature.beans.GradeType;
import com.revature.beans.ReimbursementEventType;
import com.revature.beans.TuitionReimbursementForm;
import com.revature.data.DepartmentDAO;
import com.revature.data.DepartmentDAOImpl;
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
	private static DepartmentDAO dd = new DepartmentDAOImpl();

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
				.append("supervisorname text, dept text, isdepthead boolean, pendingreimbursement double, ")
				.append("awardedreimbursement double, type text, forms list<UUID>, reviewforms list<UUID>, ")
				.append("primary key(username, firstname));");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS form (")
				.append("id uuid, issuer text, title text, description text, location text, ")
				.append("cost double, startdate date, creationdate date, creationtime time, ")
				.append("gradetype text, eventtype text, attachmenturis list<text>, ")
				.append("urgent boolean, supervisorapproved boolean, deptheadapproved boolean, ")
				.append("bencoapproved boolean, declined boolean, reasondeclined text, grade text, passed boolean, ")
				.append("awardedamount double, awardedreason text, finalcheck boolean, ")
				.append("primary key (issuer, id)); ");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS notifications (")
				.append("recipient text, message text, primary key (recipient)); ");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS departments (")
				.append("name text, deptheadusername text, primary key (name));");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

	}

	public static void populateEmployeeTable() {
		ed.addEmployee(new Employee("boss_man", "boss_password", "boss@man.com", "Boss", "Man", null, "Company", true,
				EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("sidd_mohanty", "sidd_password", "sidd@mohanty.com", "Sidd", "Mohanty", "ent",
				"Development", false, EmployeeType.REGEMPLOYEE));
		ed.addEmployee(new Employee("ham_yam", "ham_password", "ham@yam.com", "Ham", "Yam", "eieio", "Human Resources",
				false, EmployeeType.REGEMPLOYEE));
		ed.addEmployee(new Employee("sunscreen", "spf70", "its@hot.com", "Sun", "Burns", "boss_man", "Human Resources",
				true, EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("ent", "ed_password", "edward@trent.com", "Edward", "Trent", "boss_man",
				"Development", true, EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("eieio", "farm", "email.com", "Old", "McDonald", "sunscreen", "Human Resources",
				true, EmployeeType.SUPERVISOR));
		ed.addEmployee(new Employee("sponge", "bob_password", "bikini@bottom.com", "Robert", "Pants", "boss_man",
				"Benefits Coordination", true, EmployeeType.BENCO));
	}

	public static void populateFormTable() {
		TuitionReimbursementForm form = new TuitionReimbursementForm("boss_man", "FirstForm", "The first form",
				"Los Angeles", 100.0, LocalDate.of(2021, 10, 30), GradeType.LETTER, ReimbursementEventType.CERT, null);
		td.addTuitionForm(form);
	}

	public static void populateNotificationsTable() {
		nd.addNotification("boss_man", "You did it!");
	}

	public static void populateDepartmentsTable() {
		dd.addDepartment(new Department("Company", "boss_man"));
		dd.addDepartment(new Department("Development", "ent"));
		dd.addDepartment(new Department("Human Resources", "sunscreen"));
		dd.addDepartment(new Department("Benefits Coordination", "sponge"));
	}
}
