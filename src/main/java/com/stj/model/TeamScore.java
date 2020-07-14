package com.stj.model;

public class TeamScore extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Team team;
	private Integer overrideScore;
	private WeeklyScore matchA;
	private WeeklyScore matchB;
	private WeeklyScore matchC;
	private WeeklyScore matchD;
	private TeamMatch teamMatch;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getOverrideScore() {
		return overrideScore;
	}

	public void setOverrideScore(Integer overrideScore) {
		this.overrideScore = overrideScore;
	}

	public WeeklyScore getMatchA() {
		return matchA;
	}

	public void setMatchA(WeeklyScore matchA) {
		this.matchA = matchA;
	}

	public WeeklyScore getMatchB() {
		return matchB;
	}

	public void setMatchB(WeeklyScore matchB) {
		this.matchB = matchB;
	}

	public WeeklyScore getMatchC() {
		return matchC;
	}

	public void setMatchC(WeeklyScore matchC) {
		this.matchC = matchC;
	}

	public WeeklyScore getMatchD() {
		return matchD;
	}

	public void setMatchD(WeeklyScore matchD) {
		this.matchD = matchD;
	}

	public TeamMatch getTeamMatch() {
		return teamMatch;
	}

	public void setTeamMatch(TeamMatch teamMatch) {
		this.teamMatch = teamMatch;
	}

	public Integer getNetScore() {
		Integer net = new Integer(0);
		
		net += getMatchA() != null ? getMatchA().getNet() : 0;
		net += getMatchB() != null ? getMatchB().getNet() : 0;
		net += getMatchC() != null ? getMatchC().getNet() : 0;
		net += getMatchD() != null ? getMatchD().getNet() : 0;
		
		return net;
	}
	
	public boolean hasGhostPlayer() {
		return (getMatchA() != null ? getMatchA().isGhostScore() : false) || 
				(getMatchB() != null ? getMatchB().isGhostScore() : false) || 
				(getMatchC() != null ? getMatchC().isGhostScore() : false) || 
				(getMatchD() != null ? getMatchD().isGhostScore() : false);
	}
}
