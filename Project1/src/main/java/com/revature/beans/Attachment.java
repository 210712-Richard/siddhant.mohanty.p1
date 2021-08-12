package com.revature.beans;

import java.util.Objects;
import java.util.UUID;

public class Attachment {

	private AttachmentType type;
	private String URI;
	private String name;
	private UUID id;

	public Attachment() {
		super();
	}

	public Attachment(AttachmentType type, String URI, String name, UUID id) {
		this.type = type;
		this.URI = URI;
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
		return Objects.hash(URI, id, name, type);
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
		return Objects.equals(URI, other.URI) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "Attachment [type=" + type + ", URI=" + URI + ", name=" + name + ", id=" + id + "]";
	}
}
