package com.stj.model;

import com.stj.util.HoleUtils;

public class Score extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer hole_number;
	private Integer strokes;
	private WeeklyScore weeklyScore;

	public Hole getHole() {
		return HoleUtils.getHole(weeklyScore.getSide().getCourse(), hole_number);
	}

	public void setHole(Hole hole) {
		this.hole_number = hole != null ? hole.getHoleNumber() : null;
	}

	public Integer getStrokes() {
		return strokes;
	}

	public void setStrokes(Integer strokes) {
		this.strokes = strokes;
	}

	public WeeklyScore getWeeklyScore() {
		return weeklyScore;
	}

	public void setWeeklyScore(WeeklyScore weeklyScore) {
		this.weeklyScore = weeklyScore;
	}

	@SuppressWarnings("unused")
	//Used for hibernate
	private Integer getHole_number() {
		return hole_number;
	}

	@SuppressWarnings("unused")
	//Used for hibernate
	private void setHole_number(Integer holeNumber) {
		hole_number = holeNumber;
	}
}
