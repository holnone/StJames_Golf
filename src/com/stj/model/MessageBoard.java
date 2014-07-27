package com.stj.model;

public class MessageBoard extends BaseEntity {
	private static final long serialVersionUID = 1L;

	String userName;
	String message;
	Season season;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String formatForEmail() {
		String EOL = System.getProperty("line.separator");

		StringBuffer sb = new StringBuffer("");
		sb.append("Name: " + userName + EOL);
		sb.append("Message Date: " + getCreatedDate() + EOL);
		sb.append("Message: " + getMessage() + EOL);

		return sb.toString();
	}
}
