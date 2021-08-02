package com.revature.beans;

public enum ReimbursementEventType {

	UNICOURSE(0.8), SEMINAR(0.6), CERTPREP(0.75), CERT(1.0), TECHTRAIN(0.9), OTHER(0.3);

	private Double reimburseMultiplier;

	ReimbursementEventType(Double reimburseMultiplier) {
		this.reimburseMultiplier = reimburseMultiplier;
	}

	public Double getReimburseMultiplier() {
		return this.reimburseMultiplier;
	}
}
