package com.stj.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Side extends BaseEntity implements Comparable<Side> {

	private static final long serialVersionUID = 1L;

	protected String sideType;

	protected Course course;

	public abstract List<Hole> getHoles();

	public Side() {
	}

	public Side(Course course) {
		this.course = course;
	}

	public Integer getPar() {
		int par = 0;
		for (Hole hole : getHoles()) {
			par += hole.getPar();
		}
		return par;
	}

	public String getSideType() {
		return sideType;
	}

	public void setSideType(String sideType) {
		this.sideType = sideType;
	}

	public List<Hole> getHolesByHandicap() {
		List<Hole> list = getHoles();
		Collections.sort(list, new HoleHandicapComparator());
		return list;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	private class HoleHandicapComparator implements Comparator<Hole> {

		public int compare(Hole hole1, Hole hole2) {
			return new CompareToBuilder().append(hole1.getHandicap(), hole2.getHandicap()).toComparison();
		}

	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Side)) {
			return false;
		}
		Side rhs = (Side) object;
		return new EqualsBuilder().append(this.id, rhs.id).isEquals();
	}

	@Override
	public int compareTo(Side other) {
		return new CompareToBuilder().append(this.getSideType(), other.getSideType()).toComparison() * -1;
	}

}
