package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TuitionReimbursementForm {

	private String issuer;
	private String description; // description will include work time missed
	private String location;
	private Integer cost;
	private GradeType gradeType;
	private ReimbursementEventType eventType;
	private List<Attachment> attachments;
	private LocalDate creationDate;
	private LocalTime creationTime;
	private UUID id;

	public TuitionReimbursementForm() {
		this.issuer = "Nobody";		
		this.description = "Nothing";
		this.location = "Nowhere";
		this.cost = 0;
		this.gradeType = GradeType.LETTER;
		this.eventType = ReimbursementEventType.CERT;
		this.creationDate = LocalDate.now();
		this.creationTime = LocalTime.now();
	}

	public TuitionReimbursementForm(String issuer, String description, String location, Integer cost,
			GradeType gradeType, ReimbursementEventType eventType, List<Attachment> attachments) {

		this.issuer = issuer;
		this.description = description;
		this.location = location;
		this.cost = cost;
		this.gradeType = gradeType;
		this.eventType = eventType;
		this.attachments = attachments;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public LocalTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalTime creationTime) {
		this.creationTime = creationTime;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
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
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attachments, cost, creationTime, description, eventType, gradeType, id, issuer);
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
		return Objects.equals(attachments, other.attachments) && Objects.equals(cost, other.cost)
				&& Objects.equals(creationTime, other.creationTime) && Objects.equals(description, other.description)
				&& eventType == other.eventType && gradeType == other.gradeType && Objects.equals(id, other.id)
				&& Objects.equals(issuer, other.issuer);
	}

	@Override
	public String toString() {
		return "TuitionReimbursementForm [issuer=" + issuer + ", dateTime=" + creationTime + ", description=" + description
				+ ", cost=" + cost + ", gradeType=" + gradeType + ", eventType=" + eventType + ", attachments="
				+ attachments + ", id=" + id + "]";
	}
}
