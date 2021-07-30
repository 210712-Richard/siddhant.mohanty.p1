package com.revature.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
	
	private String name;
	private String password;
	private Integer id;
	private Integer reimbursement = 1000;
	private Boolean approved;
	private EmployeeType type;
	private List<String> notifications = new ArrayList<String>();

	public Employee() {
		super();
		this.type = EmployeeType.REGEMPLOYEE;
		this.notifications.add("Welcome!");
	}
	
	public Employee(String name, String password, Integer id, EmployeeType type) {
		this();
		this.name = name;
		this.password = password;
		this.id = id;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
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

	@Override
	public int hashCode() {
		return Objects.hash(approved, id, name, notifications, password, reimbursement, type);
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
		return Objects.equals(approved, other.approved) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(notifications, other.notifications)
				&& Objects.equals(password, other.password) && Objects.equals(reimbursement, other.reimbursement)
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", password=" + password + ", id=" + id + ", reimbursement=" + reimbursement
				+ ", approved=" + approved + ", type=" + type + ", notifications=" + notifications + "]";
	}
	
}
