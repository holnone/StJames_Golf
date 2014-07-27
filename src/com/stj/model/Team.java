package com.stj.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class Team extends BaseEntity implements Comparable<Team> {

	private static final long serialVersionUID = 1L;

	private Season season;
	private String name;
	private Integer teamNumber;
	private Set<Player> players = new HashSet<Player>();
	private Double points = new Double(0);
	private Integer place;

	private Set<PlayerSchedule> playerSchedules;

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

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

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public List<Player> getSortedPlayers() {
		List<Player> list = new ArrayList<Player>(getPlayers());
		Collections.sort(list);
		return list;
	}

	public int compareTo(Team other) {
		return new CompareToBuilder().append(this.getTeamNumber(), other.getTeamNumber()).toComparison();
	}

	@Override
	public String toString() {
		return teamNumber + " - " + name;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Team)) {
			return false;
		}
		Team castOther = (Team) other;
		return new EqualsBuilder().append(getTeamNumber(), castOther.getTeamNumber()).isEquals();
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public Set<PlayerSchedule> getPlayerSchedules() {
		if (playerSchedules == null) {
			playerSchedules = new HashSet<PlayerSchedule>();
		}
		return playerSchedules;
	}

	public void setPlayerSchedules(Set<PlayerSchedule> playerSchedules) {
		this.playerSchedules = playerSchedules;
	}

	public PlayerSchedule getPlayerSchedule(Week week) {
		for (PlayerSchedule schedule : getPlayerSchedules()) {
			if (week.equals(schedule.getWeek())) {
				return schedule;
			}
		}
		return new PlayerSchedule(this, week);
	}

	public void addSchedule(PlayerSchedule schedule) {
		getPlayerSchedules().add(schedule);
	}
}
