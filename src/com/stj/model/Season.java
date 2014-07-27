package com.stj.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Season extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer year;
	private Set<Round> rounds = new HashSet<Round>();
	private List<Team> teams = new ArrayList<Team>();

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Set<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Player> getLowNet() {
		Map<Integer, List<Player>> lowNetMap = new HashMap<Integer, List<Player>>();

		for (Team team : teams) {
			for (Player player : team.getPlayers()) {
				Integer lowNet = player.getLowNet(getSeasonBeginDateMinusOneDay());
				if (lowNet == null) {
					lowNet = 99;
				}
				player.setLowScore(lowNet);
				if (lowNetMap.containsKey(lowNet)) {
					lowNetMap.get(lowNet).add(player);
				} else {
					List<Player> list = new ArrayList<Player>();
					list.add(player);
					lowNetMap.put(lowNet, list);
				}
			}
		}

		List<Integer> list = new ArrayList<Integer>(lowNetMap.keySet());
		Collections.sort(list);

		if (list.get(0) == 99) {
			return new ArrayList<Player>();
		}
		List<Player> lowNetList = lowNetMap.get(list.get(0));
		Collections.sort(lowNetList);
		return lowNetList;
	}

	public List<Player> getLowGross() {
		Map<Integer, List<Player>> lowGrossMap = new HashMap<Integer, List<Player>>();

		for (Team team : teams) {
			for (Player player : team.getPlayers()) {
				Integer lowGross = player.getLowGross(getSeasonBeginDateMinusOneDay());
				if (lowGross == null) {
					lowGross = 99;
				}
				player.setLowScore(lowGross);
				if (lowGrossMap.containsKey(lowGross)) {
					lowGrossMap.get(lowGross).add(player);
				} else {
					List<Player> list = new ArrayList<Player>();
					list.add(player);
					lowGrossMap.put(lowGross, list);
				}
			}
		}

		List<Integer> list = new ArrayList<Integer>(lowGrossMap.keySet());
		Collections.sort(list);

		if (list.get(0) == 99) {
			return new ArrayList<Player>();
		}
		List<Player> lowGrossList = lowGrossMap.get(list.get(0));
		Collections.sort(lowGrossList);
		return lowGrossList;
	}

	public Map<Integer, Team> getTeamMap() {
		Map<Integer, Team> map = new HashMap<Integer, Team>();

		for (Team team : getTeams()) {
			map.put(team.getTeamNumber(), team);
		}
		return map;
	}

	private Date getSeasonBeginDateMinusOneDay() {
		List<Round> orderedRounds = new ArrayList<Round>(getRounds());
		Collections.sort(orderedRounds);
		Calendar result = Calendar.getInstance();
		result.setTime(((Round) orderedRounds.get(0)).getFirstWeek().getDate());
		result.add(Calendar.DAY_OF_MONTH, -1);
		return result.getTime();
	}

	public Week getCurrentWeek() {
		List<Week> weeks = getAllWeeks();
		Week currentWeek = weeks.get(0);
		for (Week week : weeks) {
			if (week.getDate().before(Calendar.getInstance().getTime())) {
				currentWeek = week;
			}
		}
		return currentWeek;
	}

	public List<Week> getAllWeeks() {
		List<Week> weeks = new ArrayList<Week>();
		for (Round round : getRounds()) {
			weeks.addAll(round.getWeeks());
		}
		Collections.sort(weeks);
		return weeks;
	}
}
