package com.stj.model;

import com.stj.util.HandicapUtils;

import java.util.HashMap;
import java.util.Map;

public class TeamMatch extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private boolean override = false;
	private TeamScore team1;
	private TeamScore team2;
	private Week week;
	private Course course;

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public TeamScore getTeam1() {
		return team1;
	}

	public void setTeam1(TeamScore team1) {
		this.team1 = team1;
	}

	public TeamScore getTeam2() {
		return team2;
	}

	public void setTeam2(TeamScore team2) {
		this.team2 = team2;
	}

	@Override
	public String toString() {
		return team1 != null ? team1.getTeam().getTeamNumber() + " - " + team2.getTeam().getTeamNumber() : "N/A";
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public boolean team1HasGhost() {
		return getMatchAResults().isTeam1Ghost() || getMatchBResults().isTeam1Ghost() || getMatchCResults().isTeam1Ghost()
				| getMatchDResults().isTeam1Ghost();
	}

	public boolean team2HasGhost() {
		return getMatchAResults().isTeam2Ghost() || getMatchBResults().isTeam2Ghost() || getMatchCResults().isTeam2Ghost()
				| getMatchDResults().isTeam2Ghost();
	}

	public MatchResults getMatchAResults() {
		MatchResults matchResults = new MatchResults();
		if (getTeam1() != null && getTeam1().getMatchA() != null) {
			Integer player1Handicap = getTeam1().getMatchA().getHandicap();
			Integer player2Handicap = getTeam2().getMatchA().getHandicap();
			Map<Integer, Integer> holeStrokeMap = new HashMap<Integer, Integer>();
			boolean addStrokesToPlayer1 = HandicapUtils.calculateHoleStrokes(holeStrokeMap, getTeam1().getMatchA().getSide(), player1Handicap,
					player2Handicap);
			boolean addStrokesToPlayer2 = !addStrokesToPlayer1;
			int player1HolesWon = 0;
			int player2HolesWon = 0;
			boolean isPlayer1Ghost = getTeam1() != null && getTeam1().getMatchA() != null ? getTeam1().getMatchA().isGhostScore() : false;
			boolean isPlayer2Ghost = getTeam2() != null && getTeam2().getMatchA() != null ? getTeam2().getMatchA().isGhostScore() : false;

			//HOLE 1
			Integer holeNumber = getTeam1().getMatchA().getSide().getHoles().get(0).getHoleNumber();
			int player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			int player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(1);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(1);
				player2HolesWon++;
			}
			//HOLE 2
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(1).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(2);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(2);
				player2HolesWon++;
			}
			//HOLE 3
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(2).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(3);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(3);
				player2HolesWon++;
			}
			//HOLE 4
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(3).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(4);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(4);
				player2HolesWon++;
			}
			//HOLE 5
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(4).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(5);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(5);
				player2HolesWon++;
			}
			//HOLE 6
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(5).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(6);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(6);
				player2HolesWon++;
			}
			//HOLE 7
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(6).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(7);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(7);
				player2HolesWon++;
			}
			//HOLE 8
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(7).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(8);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(8);
				player2HolesWon++;
			}
			//HOLE 9
			holeNumber = getTeam1().getMatchA().getSide().getHoles().get(8).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchA().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchA().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(9);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(9);
				player2HolesWon++;
			}
			if ((getTeam1().getMatchA().getNet() < getTeam2().getMatchA().getNet() || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.setPlayer1NetPoints(new Double(1));
				matchResults.setPlayer2NetPoints(new Double(0));
			} else if ((getTeam1().getMatchA().getNet() > getTeam2().getMatchA().getNet() || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(.5));
				matchResults.setPlayer2NetPoints(new Double(.5));
			} else {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(0));
			}

			if (player1HolesWon > player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(1));
				matchResults.setPlayer2HolePoints(new Double(0));
			} else if (player1HolesWon < player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1HolePoints(new Double(.5));
				matchResults.setPlayer2HolePoints(new Double(.5));
			} else {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(0));
			}

			matchResults.setTeam1Ghost(isPlayer1Ghost);
			matchResults.setTeam2Ghost(isPlayer2Ghost);
		}

		return matchResults;
	}

	public MatchResults getMatchBResults() {
		MatchResults matchResults = new MatchResults();
		if (getTeam1() != null && getTeam1().getMatchB() != null) {
			Integer player1Handicap = getTeam1().getMatchB().getHandicap();
			Integer player2Handicap = getTeam2().getMatchB().getHandicap();
			Map<Integer, Integer> holeStrokeMap = new HashMap<Integer, Integer>();
			boolean addStrokesToPlayer1 = HandicapUtils.calculateHoleStrokes(holeStrokeMap, getTeam1().getMatchB().getSide(), player1Handicap,
					player2Handicap);
			boolean addStrokesToPlayer2 = !addStrokesToPlayer1;
			int player1HolesWon = 0;
			int player2HolesWon = 0;
			boolean isPlayer1Ghost = getTeam1() != null && getTeam1().getMatchB() != null ? getTeam1().getMatchB().isGhostScore() : false;
			boolean isPlayer2Ghost = getTeam2() != null && getTeam2().getMatchB() != null ? getTeam2().getMatchB().isGhostScore() : false;

			//HOLE 1
			Integer holeNumber = getTeam1().getMatchB().getSide().getHoles().get(0).getHoleNumber();
			int player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			int player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(1);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(1);
				player2HolesWon++;
			}
			//HOLE 2
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(1).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(2);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(2);
				player2HolesWon++;
			}
			//HOLE 3
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(2).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(3);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(3);
				player2HolesWon++;
			}
			//HOLE 4
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(3).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(4);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(4);
				player2HolesWon++;
			}
			//HOLE 5
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(4).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(5);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(5);
				player2HolesWon++;
			}
			//HOLE 6
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(5).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(6);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(6);
				player2HolesWon++;
			}
			//HOLE 7
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(6).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(7);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(7);
				player2HolesWon++;
			}
			//HOLE 8
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(7).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(8);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(8);
				player2HolesWon++;
			}
			//HOLE 9
			holeNumber = getTeam1().getMatchB().getSide().getHoles().get(8).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchB().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchB().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(9);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(9);
				player2HolesWon++;
			}
			if ((getTeam1().getMatchB().getNet() < getTeam2().getMatchB().getNet() || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.setPlayer1NetPoints(new Double(1));
				matchResults.setPlayer2NetPoints(new Double(0));
			} else if ((getTeam1().getMatchB().getNet() > getTeam2().getMatchB().getNet() || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(.5));
				matchResults.setPlayer2NetPoints(new Double(.5));
			} else {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(0));
			}

			if (player1HolesWon > player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(1));
				matchResults.setPlayer2HolePoints(new Double(0));
			} else if (player1HolesWon < player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1HolePoints(new Double(.5));
				matchResults.setPlayer2HolePoints(new Double(.5));
			} else {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(0));
			}

			matchResults.setTeam1Ghost(isPlayer1Ghost);
			matchResults.setTeam2Ghost(isPlayer2Ghost);
		}

		return matchResults;
	}

	public MatchResults getMatchCResults() {
		MatchResults matchResults = new MatchResults();
		if (getTeam1() != null && getTeam1().getMatchC() != null) {
			Integer player1Handicap = getTeam1().getMatchC().getHandicap();
			Integer player2Handicap = getTeam2().getMatchC().getHandicap();
			Map<Integer, Integer> holeStrokeMap = new HashMap<Integer, Integer>();
			boolean addStrokesToPlayer1 = HandicapUtils.calculateHoleStrokes(holeStrokeMap, getTeam1().getMatchC().getSide(), player1Handicap,
					player2Handicap);
			boolean addStrokesToPlayer2 = !addStrokesToPlayer1;
			int player1HolesWon = 0;
			int player2HolesWon = 0;
			boolean isPlayer1Ghost = getTeam1() != null && getTeam1().getMatchC() != null ? getTeam1().getMatchC().isGhostScore() : false;
			boolean isPlayer2Ghost = getTeam2() != null && getTeam2().getMatchC() != null ? getTeam2().getMatchC().isGhostScore() : false;

			//HOLE 1
			Integer holeNumber = getTeam1().getMatchC().getSide().getHoles().get(0).getHoleNumber();
			int player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			int player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(1);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(1);
				player2HolesWon++;
			}
			//HOLE 2
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(1).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(2);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(2);
				player2HolesWon++;
			}
			//HOLE 3
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(2).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(3);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(3);
				player2HolesWon++;
			}
			//HOLE 4
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(3).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(4);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(4);
				player2HolesWon++;
			}
			//HOLE 5
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(4).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(5);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(5);
				player2HolesWon++;
			}
			//HOLE 6
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(5).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(6);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(6);
				player2HolesWon++;
			}
			//HOLE 7
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(6).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(7);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(7);
				player2HolesWon++;
			}
			//HOLE 8
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(7).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(8);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(8);
				player2HolesWon++;
			}
			//HOLE 9
			holeNumber = getTeam1().getMatchC().getSide().getHoles().get(8).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchC().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchC().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(9);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(9);
				player2HolesWon++;
			}
			if ((getTeam1().getMatchC().getNet() < getTeam2().getMatchC().getNet() || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.setPlayer1NetPoints(new Double(1));
				matchResults.setPlayer2NetPoints(new Double(0));
			} else if ((getTeam1().getMatchC().getNet() > getTeam2().getMatchC().getNet() || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(.5));
				matchResults.setPlayer2NetPoints(new Double(.5));
			} else {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(0));
			}

			if (player1HolesWon > player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(1));
				matchResults.setPlayer2HolePoints(new Double(0));
			} else if (player1HolesWon < player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1HolePoints(new Double(.5));
				matchResults.setPlayer2HolePoints(new Double(.5));
			} else {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(0));
			}

			matchResults.setTeam1Ghost(isPlayer1Ghost);
			matchResults.setTeam2Ghost(isPlayer2Ghost);
		}

		return matchResults;
	}

	public MatchResults getMatchDResults() {
		MatchResults matchResults = new MatchResults();
		if (getTeam1() != null && getTeam1().getMatchD() != null) {
			Integer player1Handicap = getTeam1().getMatchD().getHandicap();
			Integer player2Handicap = getTeam2().getMatchD().getHandicap();
			Map<Integer, Integer> holeStrokeMap = new HashMap<Integer, Integer>();
			boolean addStrokesToPlayer1 = HandicapUtils.calculateHoleStrokes(holeStrokeMap, getTeam1().getMatchD().getSide(), player1Handicap,
					player2Handicap);
			boolean addStrokesToPlayer2 = !addStrokesToPlayer1;
			int player1HolesWon = 0;
			int player2HolesWon = 0;
			boolean isPlayer1Ghost = getTeam1() != null && getTeam1().getMatchD() != null ? getTeam1().getMatchD().isGhostScore() : false;
			boolean isPlayer2Ghost = getTeam2() != null && getTeam2().getMatchD() != null ? getTeam2().getMatchD().isGhostScore() : false;

			//HOLE 1
			Integer holeNumber = getTeam1().getMatchD().getSide().getHoles().get(0).getHoleNumber();
			int player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			int player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole1Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(1);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(1);
				player2HolesWon++;
			}
			//HOLE 2
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(1).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole2Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(2);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(2);
				player2HolesWon++;
			}
			//HOLE 3
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(2).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole3Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(3);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(3);
				player2HolesWon++;
			}
			//HOLE 4
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(3).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole4Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(4);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(4);
				player2HolesWon++;
			}
			//HOLE 5
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(4).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole5Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(5);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(5);
				player2HolesWon++;
			}
			//HOLE 6\
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(5).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole6Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(6);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(6);
				player2HolesWon++;
			}
			//HOLE 7
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(6).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole7Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(7);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(7);
				player2HolesWon++;
			}
			//HOLE 8
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(7).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole8Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(8);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(8);
				player2HolesWon++;
			}
			//HOLE 9
			holeNumber = getTeam1().getMatchD().getSide().getHoles().get(8).getHoleNumber();
			player1Strokes = !isPlayer1Ghost ? (getTeam1().getMatchD().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer1 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			player2Strokes = !isPlayer2Ghost ? (getTeam2().getMatchD().getHole9Score() + (!holeStrokeMap.isEmpty() && addStrokesToPlayer2 ? holeStrokeMap
					.get(holeNumber)
					: 0))
					: 0;
			if ((player1Strokes < player2Strokes || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.getPlayer1HolesWon().add(9);
				player1HolesWon++;
			} else if ((player1Strokes > player2Strokes || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.getPlayer2HolesWon().add(9);
				player2HolesWon++;
			}
			if ((getTeam1().getMatchD().getNet() < getTeam2().getMatchD().getNet() || isPlayer2Ghost) && !isPlayer1Ghost) {
				matchResults.setPlayer1NetPoints(new Double(1));
				matchResults.setPlayer2NetPoints(new Double(0));
			} else if ((getTeam1().getMatchD().getNet() > getTeam2().getMatchD().getNet() || isPlayer1Ghost) && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1NetPoints(new Double(.5));
				matchResults.setPlayer2NetPoints(new Double(.5));
			} else {
				matchResults.setPlayer1NetPoints(new Double(0));
				matchResults.setPlayer2NetPoints(new Double(0));
			}

			if (player1HolesWon > player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(1));
				matchResults.setPlayer2HolePoints(new Double(0));
			} else if (player1HolesWon < player2HolesWon) {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(1));
			} else if (!isPlayer1Ghost && !isPlayer2Ghost) {
				matchResults.setPlayer1HolePoints(new Double(.5));
				matchResults.setPlayer2HolePoints(new Double(.5));
			} else {
				matchResults.setPlayer1HolePoints(new Double(0));
				matchResults.setPlayer2HolePoints(new Double(0));
			}

			matchResults.setTeam1Ghost(isPlayer1Ghost);
			matchResults.setTeam2Ghost(isPlayer2Ghost);
		}

		return matchResults;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void updateChildren() {
		if (getTeam1() != null) {
			if (getTeam1().getMatchA() != null) {
				getTeam1().getMatchA().setSide(
						"FT".equals(getTeam1().getMatchA().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam1().getMatchB() != null) {
				getTeam1().getMatchB().setSide(
						"FT".equals(getTeam1().getMatchB().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam1().getMatchC() != null) {
				getTeam1().getMatchC().setSide(
						"FT".equals(getTeam1().getMatchC().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam1().getMatchD() != null) {
				getTeam1().getMatchD().setSide(
						"FT".equals(getTeam1().getMatchD().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
		}
		if (getTeam2() != null) {
			if (getTeam2().getMatchA() != null) {
				getTeam2().getMatchA().setSide(
						"FT".equals(getTeam2().getMatchA().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam2().getMatchB() != null) {
				getTeam2().getMatchB().setSide(
						"FT".equals(getTeam2().getMatchB().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam2().getMatchC() != null) {
				getTeam2().getMatchC().setSide(
						"FT".equals(getTeam2().getMatchC().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
			if (getTeam2().getMatchD() != null) {
				getTeam2().getMatchD().setSide(
						"FT".equals(getTeam2().getMatchD().getSide().getSideType()) ? getCourse().getFrontNine() : getCourse().getBackNine());
			}
		}
	}
}
