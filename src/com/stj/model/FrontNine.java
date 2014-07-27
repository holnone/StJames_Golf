package com.stj.model;

import java.util.List;

import com.stj.util.HoleUtils;

public class FrontNine extends Side {

	private static final long serialVersionUID = 1L;

	public FrontNine() {
	}
	
	public FrontNine(Course course) {
		super(course);
	}
	
	public String getSideType() {
		return "FT";
	}

	@Override
	public String toString() {
		return "Front Nine";
	}

	@Override
	public List<Hole> getHoles() {
		return HoleUtils.getFrontNineHoles(course);
	}

	
}
