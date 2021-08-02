package com.revature.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private Integer id;
	private Integer reimbursement = 1000;
	private EmployeeType type;
	private List<TuitionReimbursementForm> forms = new ArrayList<TuitionReimbursementForm>();
	private List<String> notifications = new ArrayList<String>();

	public Employee() {
		super();
		this.type = EmployeeType.REGEMPLOYEE;
		this.notifications.add("Welcome!");
	}

	public Employee(String firstName, String lastName, String username, String password, Integer id,
			EmployeeType type) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.id = id;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(Integer reimbursement) {
		this.reimbursement = reimbursement;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public List<String> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<String> notifications) {
		this.notifications = notifications;
	}

	public List<TuitionReimbursementForm> getForms() {
		return forms;
	}

	public void setForms(List<TuitionReimbursementForm> forms) {
		this.forms = forms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, forms, id, lastName, notifications, password, reimbursement, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(forms, other.forms)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(notifications, other.notifications) && Objects.equals(password, other.password)
				&& Objects.equals(reimbursement, other.reimbursement) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Employee [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", id=" + id + ", reimbursement=" + reimbursement + ", type=" + type + ", forms=" + forms
				+ ", notifications=" + notifications + "]";
	}

}
