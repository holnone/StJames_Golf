package com.stj.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.joda.time.DateTime;

import java.util.*;
import java.util.Map.Entry;

public class Round extends BaseEntity implements Comparable<Round> {
	private static final long serialVersionUID = 1L;

	private Integer seasonId;
	private Set<Week> weeks = new HashSet<Week>();
	private String name;

	public Set<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(Set<Week> weeks) {
		this.weeks = weeks;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Round other) {
		return new CompareToBuilder().append(this.getWeeks().iterator().next().getDate(), other.getWeeks().iterator().next().getDate())
				.toComparison();
	}

	public boolean containsWeek(Week selectedWeek) {
		for (Week week : getWeeks()) {
			if (week.getId().equals(selectedWeek.getId())) {
				return true;
			}
		}
		return false;
	}

	public boolean containsDate(DateTime date) {
		DateTime firstWeekDate = new DateTime(getFirstWeek().getDate());
		DateTime lastWeekDate = new DateTime(getLastWeek().getDate());
		if ((firstWeekDate.isBefore(date) || firstWeekDate.isEqual(date)) && (lastWeekDate.isAfter(date) || lastWeekDate.isEqual(date))) {
			return true;
		}
		return false;
	}

	public Week getFirstWeek() {
		List<Week> orderedWeeks = new ArrayList<Week>(getWeeks());
		Collections.sort(orderedWeeks);
		return orderedWeeks.get(0);
	}

	public Week getLastWeek() {
		List<Week> orderedWeeks = new ArrayList<Week>(getWeeks());
		Collections.sort(orderedWeeks);
		return orderedWeeks.get(orderedWeeks.size() - 1);
	}

	public Week getNextWeek(Week selectedWeek) {
		List<Week> orderedWeeks = new ArrayList<Week>(getWeeks());
		Collections.sort(orderedWeeks);
		for (int i = 0; i < orderedWeeks.size(); i++) {
			Week week = orderedWeeks.get(i);
			if (week.getId().equals(selectedWeek.getId())) {
				if (i + 1 < orderedWeeks.size()) {
					return orderedWeeks.get(i + 1);
				} else {
					break;
				}
			}
		}
		return null;
	}

	public Week getPreviousWeek(Week selectedWeek) {
		List<Week> orderedWeeks = new ArrayList<Week>(getWeeks());
		Collections.sort(orderedWeeks);
		for (int i = 0; i < orderedWeeks.size(); i++) {
			Week week = orderedWeeks.get(i);
			if (week.getId().equals(selectedWeek.getId())) {
				if (i > 0) {
					return orderedWeeks.get(i - 1);
				} else {
					break;
				}
			}
		}
		return null;
	}

	public List<Team> getStandings(Season season, Date standingsDate) {
		Map<Integer, Team> teams = season.getTeamMap();
		Map<Integer, Double> teamPoints = new HashMap<Integer, Double>();
		for (Integer teamNumber : teams.keySet()) {
			teamPoints.put(teamNumber, new Double(0));
		}

		List<Week> weekList = new ArrayList<Week>(weeks);
		Collections.sort(weekList);
		for (Week week : weekList) {
			if (standingsDate != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(standingsDate);
				Calendar weekDate = Calendar.getInstance();
				weekDate.setTime(week.getDate());
				if (weekDate.after(cal)) {
					break;
				}
			}
			Integer team1Number = 0;
			Integer team2Number = 0;
			Double team1Points = new Double(0);
			Double team2Points = new Double(0);
			MatchResults matchAResults;
			MatchResults matchBResults;
			MatchResults matchCResults;
			MatchResults matchDResults;
			Integer team1Net = 0;
			Integer team2Net = 0;
			boolean team1Ghost;
			boolean team2Ghost;

			// Front Nine Tee Time 1
			if (week.getFrontNineTeeTime1() != null && week.getFrontNineTeeTime1().getTeam1() != null) {
				team1Number = week.getFrontNineTeeTime1().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getFrontNineTeeTime1().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getFrontNineTeeTime1().isOverride()) {
					team1Points = new Double(week.getFrontNineTeeTime1().getTeam1().getOverrideScore());
					team2Points = new Double(week.getFrontNineTeeTime1().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getFrontNineTeeTime1().getMatchAResults();
					matchBResults = week.getFrontNineTeeTime1().getMatchBResults();
					matchCResults = week.getFrontNineTeeTime1().getMatchCResults();
					matchDResults = week.getFrontNineTeeTime1().getMatchDResults();

					team1Net = week.getFrontNineTeeTime1() != null ? week.getFrontNineTeeTime1().getTeam1().getNetScore() : 0;
					team2Net = week.getFrontNineTeeTime1() != null ? week.getFrontNineTeeTime1().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getFrontNineTeeTime1().getTeam1().getTeam());
					teams.put(team2Number, week.getFrontNineTeeTime1().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}

			// Front Nine Tee Time 2
			if (week.getFrontNineTeeTime2() != null && week.getFrontNineTeeTime2().getTeam1() != null) {
				team1Number = week.getFrontNineTeeTime2().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getFrontNineTeeTime2().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getFrontNineTeeTime2().isOverride()) {
					team1Points = new Double(week.getFrontNineTeeTime2().getTeam1().getOverrideScore());
					team2Points = new Double(week.getFrontNineTeeTime2().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getFrontNineTeeTime2().getMatchAResults();
					matchBResults = week.getFrontNineTeeTime2().getMatchBResults();
					matchCResults = week.getFrontNineTeeTime2().getMatchCResults();
					matchDResults = week.getFrontNineTeeTime2().getMatchDResults();

					team1Net = week.getFrontNineTeeTime2() != null ? week.getFrontNineTeeTime2().getTeam1().getNetScore() : 0;
					team2Net = week.getFrontNineTeeTime2() != null ? week.getFrontNineTeeTime2().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getFrontNineTeeTime2().getTeam1().getTeam());
					teams.put(team2Number, week.getFrontNineTeeTime2().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}

			// Front Nine Tee Time 3
			if (week.getFrontNineTeeTime3() != null && week.getFrontNineTeeTime3().getTeam1() != null) {
				team1Number = week.getFrontNineTeeTime3().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getFrontNineTeeTime3().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getFrontNineTeeTime3().isOverride()) {
					team1Points = new Double(week.getFrontNineTeeTime3().getTeam1().getOverrideScore());
					team2Points = new Double(week.getFrontNineTeeTime3().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getFrontNineTeeTime3().getMatchAResults();
					matchBResults = week.getFrontNineTeeTime3().getMatchBResults();
					matchCResults = week.getFrontNineTeeTime3().getMatchCResults();
					matchDResults = week.getFrontNineTeeTime3().getMatchDResults();

					team1Net = week.getFrontNineTeeTime3() != null ? week.getFrontNineTeeTime3().getTeam1().getNetScore() : 0;
					team2Net = week.getFrontNineTeeTime3() != null ? week.getFrontNineTeeTime3().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getFrontNineTeeTime3().getTeam1().getTeam());
					teams.put(team2Number, week.getFrontNineTeeTime3().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}

			// Back Nine Tee Time 1
			if (week.getBackNineTeeTime1() != null && week.getBackNineTeeTime1().getTeam1() != null) {
				team1Number = week.getBackNineTeeTime1().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getBackNineTeeTime1().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getBackNineTeeTime1().isOverride()) {
					team1Points = new Double(week.getBackNineTeeTime1().getTeam1().getOverrideScore());
					team2Points = new Double(week.getBackNineTeeTime1().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getBackNineTeeTime1().getMatchAResults();
					matchBResults = week.getBackNineTeeTime1().getMatchBResults();
					matchCResults = week.getBackNineTeeTime1().getMatchCResults();
					matchDResults = week.getBackNineTeeTime1().getMatchDResults();

					team1Net = week.getBackNineTeeTime1() != null ? week.getBackNineTeeTime1().getTeam1().getNetScore() : 0;
					team2Net = week.getBackNineTeeTime1() != null ? week.getBackNineTeeTime1().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getBackNineTeeTime1().getTeam1().getTeam());
					teams.put(team2Number, week.getBackNineTeeTime1().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}

			// Back Nine Tee Time 2
			if (week.getBackNineTeeTime2() != null && week.getBackNineTeeTime2().getTeam1() != null) {
				team1Number = week.getBackNineTeeTime2().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getBackNineTeeTime2().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getBackNineTeeTime2().isOverride()) {
					team1Points = new Double(week.getBackNineTeeTime2().getTeam1().getOverrideScore());
					team2Points = new Double(week.getBackNineTeeTime2().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getBackNineTeeTime2().getMatchAResults();
					matchBResults = week.getBackNineTeeTime2().getMatchBResults();
					matchCResults = week.getBackNineTeeTime2().getMatchCResults();
					matchDResults = week.getBackNineTeeTime2().getMatchDResults();

					team1Net = week.getBackNineTeeTime2() != null ? week.getBackNineTeeTime2().getTeam1().getNetScore() : 0;
					team2Net = week.getBackNineTeeTime2() != null ? week.getBackNineTeeTime2().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getBackNineTeeTime2().getTeam1().getTeam());
					teams.put(team2Number, week.getBackNineTeeTime2().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}

			// Back Nine Tee Time 3
			if (week.getBackNineTeeTime3() != null && week.getBackNineTeeTime3().getTeam1() != null) {
				team1Number = week.getBackNineTeeTime3().getTeam1().getTeam().getTeamNumber();
				team2Number = week.getBackNineTeeTime3().getTeam2().getTeam().getTeamNumber();

				team1Points = new Double(0);
				team2Points = new Double(0);

				if (week.getBackNineTeeTime3().isOverride()) {
					team1Points = new Double(week.getBackNineTeeTime3().getTeam1().getOverrideScore());
					team2Points = new Double(week.getBackNineTeeTime3().getTeam2().getOverrideScore());
				} else {
					matchAResults = week.getBackNineTeeTime3().getMatchAResults();
					matchBResults = week.getBackNineTeeTime3().getMatchBResults();
					matchCResults = week.getBackNineTeeTime3().getMatchCResults();
					matchDResults = week.getBackNineTeeTime3().getMatchDResults();

					team1Net = week.getBackNineTeeTime3() != null ? week.getBackNineTeeTime3().getTeam1().getNetScore() : 0;
					team2Net = week.getBackNineTeeTime3() != null ? week.getBackNineTeeTime3().getTeam2().getNetScore() : 0;
					team1Ghost = matchAResults.isTeam1Ghost() || matchBResults.isTeam1Ghost() || matchCResults.isTeam1Ghost()
							|| matchDResults.isTeam1Ghost();
					team2Ghost = matchAResults.isTeam2Ghost() || matchBResults.isTeam2Ghost() || matchCResults.isTeam2Ghost()
							|| matchDResults.isTeam2Ghost();
					if (team1Net > 0) { // only count if there are scores
						if (((team1Net < team2Net) || team2Ghost) && !team1Ghost) {
							team1Points = new Double(2);
							team2Points = new Double(0);
						} else if (((team1Net > team2Net) || team1Ghost) && !team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(2);
						} else if (team1Ghost && team2Ghost) {
							team1Points = new Double(0);
							team2Points = new Double(0);
						} else {
							team1Points = new Double(1);
							team2Points = new Double(1);
						}
					}
					team1Points += matchAResults.getPlayer1HolePoints() + matchAResults.getPlayer1NetPoints();
					team1Points += matchBResults.getPlayer1HolePoints() + matchBResults.getPlayer1NetPoints();
					team1Points += matchCResults.getPlayer1HolePoints() + matchCResults.getPlayer1NetPoints();
					team1Points += matchDResults.getPlayer1HolePoints() + matchDResults.getPlayer1NetPoints();

					team2Points += matchAResults.getPlayer2HolePoints() + matchAResults.getPlayer2NetPoints();
					team2Points += matchBResults.getPlayer2HolePoints() + matchBResults.getPlayer2NetPoints();
					team2Points += matchCResults.getPlayer2HolePoints() + matchCResults.getPlayer2NetPoints();
					team2Points += matchDResults.getPlayer2HolePoints() + matchDResults.getPlayer2NetPoints();

					teams.put(team1Number, week.getBackNineTeeTime3().getTeam1().getTeam());
					teams.put(team2Number, week.getBackNineTeeTime3().getTeam2().getTeam());
				}
				team1Points += teamPoints.get(team1Number) != null ? teamPoints.get(team1Number) : 0;
				team2Points += teamPoints.get(team2Number) != null ? teamPoints.get(team2Number) : 0;
				teamPoints.put(team1Number, team1Points);
				teamPoints.put(team2Number, team2Points);
			}
		}

		for (Entry<Integer, Double> entry : teamPoints.entrySet()) {
			teams.get(entry.getKey()).setPoints(entry.getValue());
		}
		List<Team> teamStandings = new ArrayList<Team>(teams.values());
		Collections.sort(teamStandings, new TeamStandingComparator());
		Double points = new Double(-1);
		int place = 0;
		for (Team team : teamStandings) {
			if (!team.getPoints().equals(points)) {
				place = teamStandings.indexOf(team) + 1;
				points = team.getPoints();
			}
			team.setPlace(place);
		}
		return teamStandings;
	}

	private class TeamStandingComparator implements Comparator<Team> {
		public int compare(Team team1, Team team2) {
			return new CompareToBuilder().append(team2.getPoints(), team1.getPoints()).append(team1.getTeamNumber(), team2.getTeamNumber())
					.toComparison();
		}
	}

	public List<Player> getIndividualPoints() {
		Map<Double, List<Player>> individualMap = new HashMap<Double, List<Player>>();
		List<Player> individualList = new ArrayList<Player>();
		List<Week> weekList = new ArrayList<Week>(weeks);
		Collections.sort(weekList);
		for (Week week : weekList) {
			// Front Nine Tee Time 1
			if (week.getFrontNineTeeTime1() != null && week.getFrontNineTeeTime1().getTeam1() != null) {
				// Match A
				if (week.getFrontNineTeeTime1().getTeam1().getMatchA() != null) {
					if (!week.getFrontNineTeeTime1().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime1().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime1().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime1().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime1().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime1().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getFrontNineTeeTime1().getTeam1().getMatchB() != null) {
					if (!week.getFrontNineTeeTime1().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime1().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime1().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime1().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime1().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime1().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getFrontNineTeeTime1().getTeam1().getMatchC() != null) {
					if (!week.getFrontNineTeeTime1().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime1().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime1().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime1().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime1().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime1().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getFrontNineTeeTime1().getTeam1().getMatchD() != null) {
					if (!week.getFrontNineTeeTime1().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime1().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime1().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime1().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime1().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime1().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime1().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}

			// Front Nine Tee Time 2
			if (week.getFrontNineTeeTime2() != null && week.getFrontNineTeeTime2().getTeam1() != null) {
				// Match A
				if (week.getFrontNineTeeTime2().getTeam1().getMatchA() != null) {
					if (!week.getFrontNineTeeTime2().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime2().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime2().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime2().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime2().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime2().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getFrontNineTeeTime2().getTeam1().getMatchB() != null) {
					if (!week.getFrontNineTeeTime2().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime2().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime2().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime2().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime2().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime2().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getFrontNineTeeTime2().getTeam1().getMatchC() != null) {
					if (!week.getFrontNineTeeTime2().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime2().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime2().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime2().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime2().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime2().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getFrontNineTeeTime2().getTeam1().getMatchD() != null) {
					if (!week.getFrontNineTeeTime2().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime2().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime2().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime2().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime2().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime2().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime2().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}

			// Front Nine Tee Time 3
			if (week.getFrontNineTeeTime3() != null && week.getFrontNineTeeTime3().getTeam1() != null) {
				// Match A
				if (week.getFrontNineTeeTime3().getTeam1().getMatchA() != null) {
					if (!week.getFrontNineTeeTime3().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime3().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime3().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime3().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime3().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime3().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getFrontNineTeeTime3().getTeam1().getMatchB() != null) {
					if (!week.getFrontNineTeeTime3().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime3().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime3().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime3().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime3().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime3().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getFrontNineTeeTime3().getTeam1().getMatchC() != null) {
					if (!week.getFrontNineTeeTime3().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime3().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime3().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime3().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime3().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime3().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getFrontNineTeeTime3().getTeam1().getMatchD() != null) {
					if (!week.getFrontNineTeeTime3().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getFrontNineTeeTime3().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getFrontNineTeeTime3().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getFrontNineTeeTime3().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getFrontNineTeeTime3().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getFrontNineTeeTime3().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getFrontNineTeeTime3().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}

			// Back Nine Tee Time 1
			if (week.getBackNineTeeTime1() != null && week.getBackNineTeeTime1().getTeam1() != null) {
				// Match A
				if (week.getBackNineTeeTime1().getTeam1().getMatchD() != null) {
					if (!week.getBackNineTeeTime1().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime1().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime1().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime1().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime1().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime1().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getBackNineTeeTime1().getTeam1().getMatchB() != null) {
					if (!week.getBackNineTeeTime1().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime1().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime1().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime1().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime1().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime1().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getBackNineTeeTime1().getTeam1().getMatchC() != null) {
					if (!week.getBackNineTeeTime1().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime1().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime1().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime1().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime1().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime1().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getBackNineTeeTime1().getTeam1().getMatchD() != null) {
					if (!week.getBackNineTeeTime1().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime1().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime1().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime1().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime1().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime1().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime1().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}

			// Back Nine Tee Time 2
			if (week.getBackNineTeeTime2() != null && week.getBackNineTeeTime2().getTeam1() != null) {
				// Match A
				if (week.getBackNineTeeTime2().getTeam1().getMatchA() != null) {
					if (!week.getBackNineTeeTime2().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime2().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime2().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime2().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime2().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime2().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getBackNineTeeTime2().getTeam1().getMatchB() != null) {
					if (!week.getBackNineTeeTime2().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime2().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime2().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime2().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime2().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime2().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getBackNineTeeTime2().getTeam1().getMatchC() != null) {
					if (!week.getBackNineTeeTime2().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime2().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime2().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime2().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime2().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime2().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getBackNineTeeTime2().getTeam1().getMatchD() != null) {
					if (!week.getBackNineTeeTime2().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime2().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime2().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime2().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime2().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime2().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime2().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}

			// Back Nine Tee Time 3
			if (week.getBackNineTeeTime3() != null && week.getBackNineTeeTime3().getTeam1() != null) {
				// Match A
				if (week.getBackNineTeeTime3().getTeam1().getMatchA() != null) {
					if (!week.getBackNineTeeTime3().getMatchAResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime3().getTeam1().getMatchA().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchAResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime3().getMatchAResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime3().getMatchAResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime3().getTeam2().getMatchA().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchAResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime3().getMatchAResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match B
				if (week.getBackNineTeeTime3().getTeam1().getMatchB() != null) {
					if (!week.getBackNineTeeTime3().getMatchBResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime3().getTeam1().getMatchB().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchBResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime3().getMatchBResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime3().getMatchBResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime3().getTeam2().getMatchB().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchBResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime3().getMatchBResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match C
				if (week.getBackNineTeeTime3().getTeam1().getMatchC() != null) {
					if (!week.getBackNineTeeTime3().getMatchCResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime3().getTeam1().getMatchC().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchCResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime3().getMatchCResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime3().getMatchCResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime3().getTeam2().getMatchC().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchCResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime3().getMatchCResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
				// Match D
				if (week.getBackNineTeeTime3().getTeam1().getMatchD() != null) {
					if (!week.getBackNineTeeTime3().getMatchDResults().isTeam1Ghost()) {
						Player player1 = week.getBackNineTeeTime3().getTeam1().getMatchD().getPlayer();
						if (individualList.contains(player1)) {
							individualList.get(individualList.indexOf(player1)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchDResults().getPlayer1TotalPoints());
						} else {
							player1.setIndividualPoints(week.getBackNineTeeTime3().getMatchDResults().getPlayer1TotalPoints());
							individualList.add(player1);
						}
					}
					if (!week.getBackNineTeeTime3().getMatchDResults().isTeam2Ghost()) {
						Player player2 = week.getBackNineTeeTime3().getTeam2().getMatchD().getPlayer();
						if (individualList.contains(player2)) {
							individualList.get(individualList.indexOf(player2)).addIndividualPoints(
									week.getBackNineTeeTime3().getMatchDResults().getPlayer2TotalPoints());
						} else {
							player2.setIndividualPoints(week.getBackNineTeeTime3().getMatchDResults().getPlayer2TotalPoints());
							individualList.add(player2);
						}
					}
				}
			}
		}

		if (individualList.isEmpty()) {
			return new ArrayList<Player>();
		}
		for (Player player : individualList) {
			if (individualMap.containsKey(player.getIndividualPoints())) {
				if (individualMap.get(player.getIndividualPoints()) == null) {
					List<Player> list = new ArrayList<Player>();
					list.add(player);
					individualMap.put(player.getIndividualPoints(), list);
				} else {
					individualMap.get(player.getIndividualPoints()).add(player);
				}
			} else {
				List<Player> list = new ArrayList<Player>();
				list.add(player);
				individualMap.put(player.getIndividualPoints(), list);
			}
		}

		List<Double> list = new ArrayList<Double>(individualMap.keySet());
		Collections.sort(list); // sorts ascending
		Collections.reverse(list); // reverse to get highest first

		List<Player> sortedList = individualMap.get(list.get(0));
		Collections.sort(sortedList);

		List<Player> results = new ArrayList<Player>();
		results.addAll(sortedList);

		if (list.size() > 1) {
			sortedList = individualMap.get(list.get(1));
			Collections.sort(sortedList);
			results.addAll(sortedList);
		}
		return results;
	}
}
