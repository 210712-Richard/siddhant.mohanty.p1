package com.revature.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Employee {

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String supervisorName;
	private String dept;
	private Double pendingReimbursement;
	private Double awardedReimbursement;
	private EmployeeType type;
	// Forms submitted by the employee that do not have any action on them
	private List<UUID> forms = new ArrayList<UUID>();
	// Forms submitted by the employee that have been looked at and have been
	// returned to the employee for further review
	private List<UUID> reviewForms = new ArrayList<UUID>();
	private List<String> notifications = new ArrayList<String>();

	public Employee() {
		super();
		this.pendingReimbursement = 0.0;
		this.awardedReimbursement = 0.0;
		this.type = EmployeeType.REGEMPLOYEE;
		this.notifications.add("Welcome!");
	}

	public Employee(String username, String password, String email, String firstName, String lastName,
			String supervisorName, String dept, EmployeeType type) {
		this();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.supervisorName = supervisorName;
		this.dept = dept;
		this.type = type;
	}

	public List<UUID> getReviewForms() {
		return reviewForms;
	}

	public void setReviewForms(List<UUID> reviewForms) {
		this.reviewForms = reviewForms;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Double getPendingReimbursement() {
		return pendingReimbursement;
	}

	public void setPendingReimbursement(Double pendingReimbursement) {
		this.pendingReimbursement = pendingReimbursement;
	}

	public Double getAwardedReimbursement() {
		return awardedReimbursement;
	}

	public void setAwardedReimbursement(Double awardedReimbursement) {
		this.awardedReimbursement = awardedReimbursement;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<UUID> getForms() {
		return forms;
	}

	public void setForms(List<UUID> forms) {
		this.forms = forms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(awardedReimbursement, dept, email, firstName, forms, lastName, notifications, password,
				pendingReimbursement, reviewForms, supervisorName, type, username);
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
		return Objects.equals(awardedReimbursement, other.awardedReimbursement) && Objects.equals(dept, other.dept)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(forms, other.forms) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(notifications, other.notifications) && Objects.equals(password, other.password)
				&& Objects.equals(pendingReimbursement, other.pendingReimbursement)
				&& Objects.equals(reviewForms, other.reviewForms)
				&& Objects.equals(supervisorName, other.supervisorName) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Employee [username=" + username + ", password=" + password + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", supervisorName=" + supervisorName + ", dept=" + dept
				+ ", pendingReimbursement=" + pendingReimbursement + ", awardedReimbursement=" + awardedReimbursement
				+ ", type=" + type + ", forms=" + forms + ", reviewForms=" + reviewForms + ", notifications="
				+ notifications + "]";
	}

}
