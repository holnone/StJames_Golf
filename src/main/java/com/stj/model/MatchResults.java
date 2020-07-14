package com.stj.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchResults implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Integer> player1HolesWon = new ArrayList<Integer>();
	private List<Integer> player2HolesWon = new ArrayList<Integer>();

	private Double player1HolePoints = new Double(0);
	private Double player1NetPoints = new Double(0);
	private Double player2HolePoints = new Double(0);
	private Double player2NetPoints = new Double(0);

	private boolean team1Ghost;
	private boolean team2Ghost;

	public Double getPlayer1HolePoints() {
		return player1HolePoints;
	}

	public void setPlayer1HolePoints(Double player1HolePoints) {
		this.player1HolePoints = player1HolePoints;
	}

	public Double getPlayer1NetPoints() {
		return player1NetPoints;
	}

	public void setPlayer1NetPoints(Double player1NetPoints) {
		this.player1NetPoints = player1NetPoints;
	}

	public Double getPlayer2HolePoints() {
		return player2HolePoints;
	}

	public void setPlayer2HolePoints(Double player2HolePoints) {
		this.player2HolePoints = player2HolePoints;
	}

	public Double getPlayer2NetPoints() {
		return player2NetPoints;
	}

	public void setPlayer2NetPoints(Double player2NetPoints) {
		this.player2NetPoints = player2NetPoints;
	}

	public boolean isTeam1Ghost() {
		return team1Ghost;
	}

	public void setTeam1Ghost(boolean team1Ghost) {
		this.team1Ghost = team1Ghost;
	}

	public boolean isTeam2Ghost() {
		return team2Ghost;
	}

	public void setTeam2Ghost(boolean team2Ghost) {
		this.team2Ghost = team2Ghost;
	}

	public List<Integer> getPlayer1HolesWon() {
		return player1HolesWon;
	}

	public void setPlayer1HolesWon(List<Integer> player1HolesWon) {
		this.player1HolesWon = player1HolesWon;
	}

	public List<Integer> getPlayer2HolesWon() {
		return player2HolesWon;
	}

	public void setPlayer2HolesWon(List<Integer> player2HolesWon) {
		this.player2HolesWon = player2HolesWon;
	}

	public Double getPlayer1TotalPoints() {
		return getPlayer1HolePoints() + getPlayer1NetPoints();
	}
	
	public Double getPlayer2TotalPoints() {
		return getPlayer2HolePoints() + getPlayer2NetPoints();
	}
}
