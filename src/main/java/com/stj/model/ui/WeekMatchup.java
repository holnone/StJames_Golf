package com.stj.model.ui;

import com.stj.model.*;

import java.util.ArrayList;
import java.util.List;

public class WeekMatchup {
	private Week week;
	private TeamMatch teamMatch;
	private Team team1;
	private Team team2;

	private Side matchASide;
	private Player matchATeam1Player;
	private Player matchATeam2Player;
	private List<String> matchATeam1Holes = new ArrayList<String>();
	private List<String> matchATeam2Holes = new ArrayList<String>();

	private Side matchBSide;
	private Player matchBTeam1Player;
	private Player matchBTeam2Player;
	private List<String> matchBTeam1Holes = new ArrayList<String>();
	private List<String> matchBTeam2Holes = new ArrayList<String>();

	private Side matchCSide;
	private Player matchCTeam1Player;
	private Player matchCTeam2Player;
	private List<String> matchCTeam1Holes = new ArrayList<String>();
	private List<String> matchCTeam2Holes = new ArrayList<String>();

	private Side matchDSide;
	private Player matchDTeam1Player;
	private Player matchDTeam2Player;
	private List<String> matchDTeam1Holes = new ArrayList<String>();
	private List<String> matchDTeam2Holes = new ArrayList<String>();

	public void reset() {
		
	}
	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public TeamMatch getTeamMatch() {
		return teamMatch;
	}

	public void setTeamMatch(TeamMatch teamMatch) {
		this.teamMatch = teamMatch;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Side getMatchASide() {
		return matchASide;
	}

	public void setMatchASide(Side matchASide) {
		this.matchASide = matchASide;
	}

	public Player getMatchATeam1Player() {
		return matchATeam1Player;
	}

	public void setMatchATeam1Player(Player matchATeam1Player) {
		this.matchATeam1Player = matchATeam1Player;
	}

	public Player getMatchATeam2Player() {
		return matchATeam2Player;
	}

	public void setMatchATeam2Player(Player matchATeam2Player) {
		this.matchATeam2Player = matchATeam2Player;
	}

	public List<String> getMatchATeam1Holes() {
		return matchATeam1Holes;
	}

	public void setMatchATeam1Holes(List<String> matchATeam1Holes) {
		this.matchATeam1Holes = matchATeam1Holes;
	}

	public List<String> getMatchATeam2Holes() {
		return matchATeam2Holes;
	}

	public void setMatchATeam2Holes(List<String> matchATeam2Holes) {
		this.matchATeam2Holes = matchATeam2Holes;
	}

	public Side getMatchBSide() {
		return matchBSide;
	}

	public void setMatchBSide(Side matchBSide) {
		this.matchBSide = matchBSide;
	}

	public Player getMatchBTeam1Player() {
		return matchBTeam1Player;
	}

	public void setMatchBTeam1Player(Player matchBTeam1Player) {
		this.matchBTeam1Player = matchBTeam1Player;
	}

	public Player getMatchBTeam2Player() {
		return matchBTeam2Player;
	}

	public void setMatchBTeam2Player(Player matchBTeam2Player) {
		this.matchBTeam2Player = matchBTeam2Player;
	}

	public List<String> getMatchBTeam1Holes() {
		return matchBTeam1Holes;
	}

	public void setMatchBTeam1Holes(List<String> matchBTeam1Holes) {
		this.matchBTeam1Holes = matchBTeam1Holes;
	}

	public List<String> getMatchBTeam2Holes() {
		return matchBTeam2Holes;
	}

	public void setMatchBTeam2Holes(List<String> matchBTeam2Holes) {
		this.matchBTeam2Holes = matchBTeam2Holes;
	}

	public Side getMatchCSide() {
		return matchCSide;
	}

	public void setMatchCSide(Side matchCSide) {
		this.matchCSide = matchCSide;
	}

	public Player getMatchCTeam1Player() {
		return matchCTeam1Player;
	}

	public void setMatchCTeam1Player(Player matchCTeam1Player) {
		this.matchCTeam1Player = matchCTeam1Player;
	}

	public Player getMatchCTeam2Player() {
		return matchCTeam2Player;
	}

	public void setMatchCTeam2Player(Player matchCTeam2Player) {
		this.matchCTeam2Player = matchCTeam2Player;
	}

	public List<String> getMatchCTeam1Holes() {
		return matchCTeam1Holes;
	}

	public void setMatchCTeam1Holes(List<String> matchCTeam1Holes) {
		this.matchCTeam1Holes = matchCTeam1Holes;
	}

	public List<String> getMatchCTeam2Holes() {
		return matchCTeam2Holes;
	}

	public void setMatchCTeam2Holes(List<String> matchCTeam2Holes) {
		this.matchCTeam2Holes = matchCTeam2Holes;
	}

	public Side getMatchDSide() {
		return matchDSide;
	}

	public void setMatchDSide(Side matchDSide) {
		this.matchDSide = matchDSide;
	}

	public Player getMatchDTeam1Player() {
		return matchDTeam1Player;
	}

	public void setMatchDTeam1Player(Player matchDTeam1Player) {
		this.matchDTeam1Player = matchDTeam1Player;
	}

	public Player getMatchDTeam2Player() {
		return matchDTeam2Player;
	}

	public void setMatchDTeam2Player(Player matchDTeam2Player) {
		this.matchDTeam2Player = matchDTeam2Player;
	}

	public List<String> getMatchDTeam1Holes() {
		return matchDTeam1Holes;
	}

	public void setMatchDTeam1Holes(List<String> matchDTeam1Holes) {
		this.matchDTeam1Holes = matchDTeam1Holes;
	}

	public List<String> getMatchDTeam2Holes() {
		return matchDTeam2Holes;
	}

	public void setMatchDTeam2Holes(List<String> matchDTeam2Holes) {
		this.matchDTeam2Holes = matchDTeam2Holes;
	}
}
