package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TuitionReimbursementForm {

	private UUID id;
	private String issuer;
	private String title;
	private String description; // description will include work time missed
	private String location;
	private Double cost;
	private LocalDate startDate;
	private LocalDate creationDate;
	private LocalTime creationTime;
	private GradeType gradeType;
	private ReimbursementEventType eventType;
	private List<Attachment> attachments;
	private Boolean urgent;
	private Boolean supervisorApproved;
	private Boolean deptHeadApproved;
	private Boolean benCoApproved;
	private Boolean declined;
	private String reasonDeclined;
	private String grade;
	private Boolean passed;
	private Double awardedAmount;
	private String awardedReason; // If awarded reimbursement was changed
	private Boolean finalCheck; // Did issuer check the form after it was sent back to them by the benco

	public TuitionReimbursementForm() {
		this.id = UUID.randomUUID();
		this.creationDate = LocalDate.now();
		this.creationTime = LocalTime.now();
		this.urgent = false;
		this.declined = false;
	}

	public TuitionReimbursementForm(String issuer, String title, String description, String location, Double cost,
			LocalDate startDate, GradeType gradeType,
			ReimbursementEventType eventType, List<Attachment> attachments) {
		this();
		this.issuer = issuer;
		this.description = description;
		this.location = location;
		this.cost = cost;
		this.startDate = startDate;
		this.gradeType = gradeType;
		this.eventType = eventType;
		this.attachments = attachments;
	}
	
	public Boolean getDeclined() {
		return declined;
	}

	public void setDeclined(Boolean declined) {
		this.declined = declined;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	public ReimbursementEventType getEventType() {
		return eventType;
	}

	public void setEventType(ReimbursementEventType eventType) {
		this.eventType = eventType;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Boolean getUrgent() {
		return urgent;
	}

	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}

	public Boolean getSupervisorApproved() {
		return supervisorApproved;
	}

	public void setSupervisorApproved(Boolean supervisorApproved) {
		this.supervisorApproved = supervisorApproved;
	}

	public Boolean getDeptHeadApproved() {
		return deptHeadApproved;
	}

	public void setDeptHeadApproved(Boolean deptHeadApproved) {
		this.deptHeadApproved = deptHeadApproved;
	}

	public Boolean getBenCoApproved() {
		return benCoApproved;
	}

	public void setBenCoApproved(Boolean benCoApproved) {
		this.benCoApproved = benCoApproved;
	}

	public String getReasonDeclined() {
		return reasonDeclined;
	}

	public void setReasonDeclined(String reasonDeclined) {
		this.reasonDeclined = reasonDeclined;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Boolean getPassed() {
		return passed;
	}

	public void setPassed(Boolean passed) {
		this.passed = passed;
	}

	public Double getAwardedAmount() {
		return awardedAmount;
	}

	public void setAwardedAmount(Double awardedAmount) {
		this.awardedAmount = awardedAmount;
	}

	public String getAwardedReason() {
		return awardedReason;
	}

	public void setAwardedReason(String awardedReason) {
		this.awardedReason = awardedReason;
	}

	public Boolean getFinalCheck() {
		return finalCheck;
	}

	public void setFinalCheck(Boolean finalCheck) {
		this.finalCheck = finalCheck;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attachments, awardedAmount, awardedReason, benCoApproved, cost, creationDate, creationTime,
				declined, deptHeadApproved, description, eventType, finalCheck, grade, gradeType, id, issuer, location,
				passed, reasonDeclined, startDate, supervisorApproved, title, urgent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TuitionReimbursementForm other = (TuitionReimbursementForm) obj;
		return Objects.equals(attachments, other.attachments) && Objects.equals(awardedAmount, other.awardedAmount)
				&& Objects.equals(awardedReason, other.awardedReason)
				&& Objects.equals(benCoApproved, other.benCoApproved) && Objects.equals(cost, other.cost)
				&& Objects.equals(creationDate, other.creationDate) && Objects.equals(creationTime, other.creationTime)
				&& Objects.equals(declined, other.declined) && Objects.equals(deptHeadApproved, other.deptHeadApproved)
				&& Objects.equals(description, other.description) && eventType == other.eventType
				&& Objects.equals(finalCheck, other.finalCheck) && Objects.equals(grade, other.grade)
				&& gradeType == other.gradeType && Objects.equals(id, other.id) && Objects.equals(issuer, other.issuer)
				&& Objects.equals(location, other.location) && Objects.equals(passed, other.passed)
				&& Objects.equals(reasonDeclined, other.reasonDeclined) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(supervisorApproved, other.supervisorApproved) && Objects.equals(title, other.title)
				&& Objects.equals(urgent, other.urgent);
	}

	@Override
	public String toString() {
		return "TuitionReimbursementForm [id=" + id + ", issuer=" + issuer + ", title=" + title + ", description="
				+ description + ", location=" + location + ", cost=" + cost + ", startDate=" + startDate
				+ ", creationDate=" + creationDate + ", creationTime=" + creationTime + ", gradeType=" + gradeType
				+ ", eventType=" + eventType + ", attachments=" + attachments + ", urgent=" + urgent
				+ ", supervisorApproved=" + supervisorApproved + ", deptHeadApproved=" + deptHeadApproved
				+ ", benCoApproved=" + benCoApproved + ", declined=" + declined + ", reasonDeclined=" + reasonDeclined
				+ ", grade=" + grade + ", passed=" + passed + ", awardedAmount=" + awardedAmount + ", awardedReason="
				+ awardedReason + ", finalCheck=" + finalCheck + "]";
	}
}