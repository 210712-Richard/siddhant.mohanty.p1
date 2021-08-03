package com.revature.beans;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TuitionReimbursementForm {

	private String issuer;
	private LocalDateTime creationTime;
	private String description; // description will include work time missed
	private Integer cost;
	private GradeType gradeType;
	private ReimbursementEventType eventType;
	private List<Attachment> attachments;
	private UUID id;

	public TuitionReimbursementForm() {
		this.issuer = "Nobody";
		this.creationTime = LocalDateTime.now();
		this.description = "Nothing";
		this.cost = 0;
		this.gradeType = GradeType.LETTER;
		this.eventType = ReimbursementEventType.CERT;
	}

	public TuitionReimbursementForm(String issuer, LocalDateTime dateTime, String description, Integer cost,
			GradeType gradeType, ReimbursementEventType eventType, List<Attachment> attachments) {

		this.issuer = issuer;
		this.creationTime = dateTime;
		this.description = description;
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

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime dateTime) {
		this.creationTime = dateTime;
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
