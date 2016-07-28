package org.nhnnext.domain;

public enum UserType {
	TRELLO(Values.TRELLO), GITHUB(Values.GITHUB);

	private String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static class Values {
		public static final String TRELLO = "S";
		public static final String GITHUB = "G";
	}
}
