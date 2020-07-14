package com.stj.model;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	private String createdUserId;
	private Date createdDate;
	private String lastUpdatedUserId;
	private Date lastUpdatedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedUserId() {
		return lastUpdatedUserId;
	}

	public void setLastUpdatedUserId(String lastUpdatedUserId) {
		this.lastUpdatedUserId = lastUpdatedUserId;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
