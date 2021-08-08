package com.revature.beans;

import java.util.Objects;

public class Attachment {

	private AttachmentType type;
	private String URI;

	public Attachment() {
		this.type = AttachmentType.PDF;
		this.URI = "";
	}

	public Attachment(AttachmentType type, String URI) {
		this.type = type;
		this.URI = URI;
	}

	public AttachmentType getType() {
		return type;
	}

	public void setType(AttachmentType type) {
		this.type = type;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String URI) {
		this.URI = URI;
	}

	@Override
	public int hashCode() {
		return Objects.hash(URI, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		return Objects.equals(URI, other.URI) && type == other.type;
	}

	@Override
	public String toString() {
		return "Attachment [type=" + type + ", URI=" + URI + "]";
	}
}
