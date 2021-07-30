package com.revature.beans;

import java.util.Objects;

public class Attachment {

	private AttachmentType type;
	private String body;
	
	public Attachment() {
		this.type = AttachmentType.PDF;
		this.body = "Nothing here";
	}
	
	public Attachment(AttachmentType type, String body) {
		this.type = type;
		this.body = body;
	}

	public AttachmentType getType() {
		return type;
	}

	public void setType(AttachmentType type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, type);
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
		return Objects.equals(body, other.body) && type == other.type;
	}

	@Override
	public String toString() {
		return "Attachment [type=" + type + ", body=" + body + "]";
	}
}
