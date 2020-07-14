package com.stj.model;

import com.stj.util.HoleUtils;

import java.util.List;

public class BackNine extends Side {
	private static final long serialVersionUID = 1L;

	public BackNine() {
	}

	public BackNine(Course course) {
		super(course);
	}
	
	public String getSideType() {
		return "BK";
	}

	@Override
	public String toString() {
		return "Back Nine";
	}

	@Override
	public List<Hole> getHoles() {
		return HoleUtils.getBackNineHoles(course);
	}
}
