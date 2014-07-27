package com.stj.external.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ExternalTeam implements Serializable, Comparable<ExternalTeam> {

	private static final long serialVersionUID = -1L;

	@Element
	private String name;

	@Element
	private Integer teamNumber;

	@Element
	private Double points = new Double(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(Integer teamNumber) {
		this.teamNumber = teamNumber;
	}

	public int compareTo(ExternalTeam other) {
		return new CompareToBuilder().append(this.getTeamNumber(), other.getTeamNumber()).toComparison();
	}

	public String toString() {
		return teamNumber + " - " + name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ExternalTeam)) {
			return false;
		}
		ExternalTeam castOther = (ExternalTeam) other;
		return new EqualsBuilder().append(getTeamNumber(), castOther.getTeamNumber()).isEquals();
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

}
