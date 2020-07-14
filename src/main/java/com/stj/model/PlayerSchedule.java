package com.stj.model;

import java.util.HashSet;
import java.util.Set;

public class PlayerSchedule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Team team;
	private Week week;
	private Set<Player> players;

	private PlayerSchedule() {

	}

	public PlayerSchedule(Team team, Week week) {
		this.team = team;
		this.week = week;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public Set<Player> getPlayers() {
		if (players == null) {
			players = new HashSet<Player>();
		}
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

}
