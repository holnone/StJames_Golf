package com.stj.web.aspect;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AuditInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 1L;

	private List<String> lastUpdatedUserPropertyNames = Collections.emptyList();

	private List<String> lastUpdatedDatePropertyNames = Collections.emptyList();

	private List<String> createdUserPropertyNames = Collections.emptyList();

	private List<String> createdDatePropertyNames = Collections.emptyList();

	public void setCreatedDatePropertyNames(
			List<String> createdDatePropertyNames) {
		if (createdDatePropertyNames != null) {
			this.createdDatePropertyNames = createdDatePropertyNames;
		}
	}

	public void setCreatedUserPropertyNames(
			List<String> createdUserPropertyNames) {
		if (createdUserPropertyNames != null) {
			this.createdUserPropertyNames = createdUserPropertyNames;
		}
	}

	public void setLastUpdatedDatePropertyNames(
			List<String> lastUpdatedDatePropertyNames) {
		if (lastUpdatedDatePropertyNames != null) {
			this.lastUpdatedDatePropertyNames = lastUpdatedDatePropertyNames;
		}
	}

	public void setLastUpdatedUserPropertyNames(
			List<String> lastUpdatedUserPropertyNames) {
		if (lastUpdatedUserPropertyNames != null) {
			this.lastUpdatedUserPropertyNames = lastUpdatedUserPropertyNames;
		}
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		boolean result = false;

		for (int i = 0; i < propertyNames.length; i++) {
			if (isLastUpdatedUserIdSynonym(propertyNames[i])) {
				currentState[i] = getUserId();
				result = true;
			} else if (isLastUpdatedDateSynonym(propertyNames[i])) {
				currentState[i] = new Date();
				result = true;
			}
		}

		return result;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		boolean result = false;
		Date now = new Date();

		for (int i = 0; i < propertyNames.length; i++) {
			if (isLastUpdatedUserIdSynonym(propertyNames[i])) {
				state[i] = getUserId();
				result = true;
			} else if (isLastUpdatedDateSynonym(propertyNames[i])) {
				state[i] = now;
				result = true;
			} else if (isCreatedUserIdSynonym(propertyNames[i])) {
				state[i] = getUserId();
				result = true;
			} else if (isCreatedDateSynonym(propertyNames[i])) {
				state[i] = now;
				result = true;
			}
		}

		return result;
	}

	private boolean isLastUpdatedUserIdSynonym(String propertyName) {
		return this.lastUpdatedUserPropertyNames.contains(propertyName);
	}

	private boolean isLastUpdatedDateSynonym(String propertyName) {
		return this.lastUpdatedDatePropertyNames.contains(propertyName);
	}

	private boolean isCreatedUserIdSynonym(String propertyName) {
		return this.createdUserPropertyNames.contains(propertyName);
	}

	private boolean isCreatedDateSynonym(String propertyName) {
		return this.createdDatePropertyNames.contains(propertyName);
	}

	private String getUserId() {
		return "SYSTEM";
	}
}
