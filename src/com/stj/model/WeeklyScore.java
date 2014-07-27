package com.stj.model;

public class WeeklyScore extends PlayerScore {

	private static final long serialVersionUID = 1L;

	private Integer handicap;
	private Integer hole1Score;
	private Integer hole2Score;
	private Integer hole3Score;
	private Integer hole4Score;
	private Integer hole5Score;
	private Integer hole6Score;
	private Integer hole7Score;
	private Integer hole8Score;
	private Integer hole9Score;
	private boolean ghostScore = false;
	private TeamScore teamScore;

	public Integer getHandicap() {
		return handicap;
	}

	public void setHandicap(Integer handicap) {
		this.handicap = handicap;
	}

	@Override
	public Integer getScore() {
		return !isGhostScore() ? hole1Score + hole2Score + hole3Score + hole4Score + hole5Score + hole6Score + hole7Score + hole8Score + hole9Score
				: 0;
	}

	public boolean isGhostScore() {
		return ghostScore;
	}

	public void setGhostScore(boolean ghostScore) {
		this.ghostScore = ghostScore;
	}

	public Integer getNet() {
		return this.getScore() - (this.getHandicap() != null ? this.getHandicap() : 0);
	}

	public Integer getHole1Score() {
		return hole1Score;
	}

	public void setHole1Score(Integer hole1Score) {
		this.hole1Score = hole1Score;
	}

	public Integer getHole2Score() {
		return hole2Score;
	}

	public void setHole2Score(Integer hole2Score) {
		this.hole2Score = hole2Score;
	}

	public Integer getHole3Score() {
		return hole3Score;
	}

	public void setHole3Score(Integer hole3Score) {
		this.hole3Score = hole3Score;
	}

	public Integer getHole4Score() {
		return hole4Score;
	}

	public void setHole4Score(Integer hole4Score) {
		this.hole4Score = hole4Score;
	}

	public Integer getHole5Score() {
		return hole5Score;
	}

	public void setHole5Score(Integer hole5Score) {
		this.hole5Score = hole5Score;
	}

	public Integer getHole6Score() {
		return hole6Score;
	}

	public void setHole6Score(Integer hole6Score) {
		this.hole6Score = hole6Score;
	}

	public Integer getHole7Score() {
		return hole7Score;
	}

	public void setHole7Score(Integer hole7Score) {
		this.hole7Score = hole7Score;
	}

	public Integer getHole8Score() {
		return hole8Score;
	}

	public void setHole8Score(Integer hole8Score) {
		this.hole8Score = hole8Score;
	}

	public Integer getHole9Score() {
		return hole9Score;
	}

	public void setHole9Score(Integer hole9Score) {
		this.hole9Score = hole9Score;
	}

	public TeamScore getTeamScore() {
		return teamScore;
	}

	public void setTeamScore(TeamScore teamScore) {
		this.teamScore = teamScore;
	}

	public Integer getHoleScore(int score) {
		switch (score) {
		case 1:
			return hole1Score;
		case 2:
			return hole2Score;
		case 3:
			return hole3Score;
		case 4:
			return hole4Score;
		case 5:
			return hole5Score;
		case 6:
			return hole6Score;
		case 7:
			return hole7Score;
		case 8:
			return hole8Score;
		case 9:
			return hole9Score;
		default:
			return 99;
		}
	}
}
