package com.dev.ops.uuid.service.domain;

public enum EntityType {
	//@formatter:off
	EMPLOYEE(""),
	PROJECT("P-");
	//@formatter:on

	private String prefix;

	private EntityType(final String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}
}
