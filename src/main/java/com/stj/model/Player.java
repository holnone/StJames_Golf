package com.stj.model;

import com.stj.util.HandicapUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.*;

public class Player extends BaseEntity implements Comparable<Player> {

	private static final long serialVersionUID = 1L;

	private Name playerName;
	private Team team;
	private Set<WeeklyScore> scores = new HashSet<WeeklyScore>();
	private Set<PreRound> preRounds = new HashSet<PreRound>();
	private Integer lowScore;
	private boolean active;
	private Date skinsStartDate;
	private Date seniorStartDate;

	private Double individualPoints = new Double(0.0);

	public Name getPlayerName() {
		return playerName;
	}

	public void setPlayerName(Name playerName) {
		this.playerName = playerName;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Set<WeeklyScore> getScores() {
		return scores;
	}

	public void setScores(Set<WeeklyScore> scores) {
		this.scores = scores;
	}

	public List<PlayerScore> getLast5Rounds(Date currentWeek) {
		List<PlayerScore> list = new ArrayList<PlayerScore>();
		list.addAll(getScores());
		list.addAll(getPreRounds());
		Collections.sort(list);
		Collections.reverse(list);

		if (currentWeek != null || getSeniorStartDate() != null) {
			for (Iterator<PlayerScore> iter = list.iterator(); iter.hasNext();) {
				PlayerScore score = iter.next();
				if ((currentWeek != null && !score.getScoreDate().before(currentWeek)) ||
						(getSeniorStartDate() != null && !score.getScoreDate().after(getSeniorStartDate()))) {
					iter.remove();
				}
			}
		}
		
		List<PlayerScore> last5Rounds = new ArrayList<PlayerScore>();
		if (list.size() > 4) {
			last5Rounds = new ArrayList<PlayerScore>(list.subList(0, 5));
		} else if (list.size() > 0) {
			last5Rounds = new ArrayList<PlayerScore>(list.subList(0, list.size()));
		}
		Collections.reverse(last5Rounds);
		return last5Rounds;
	}

	public String getLast5Rounds_String() {
		StringBuffer buffer = new StringBuffer();
		for (PlayerScore score : getLast5Rounds(null)) {
			if (buffer.length() > 0) {
				buffer.append(", ");
			}
			buffer.append(score.getScore());
		}
		return buffer.toString();
	}

	public Integer getCurrentHandicap(Date currentWeek) {
		Integer hdcp = null;
		int count = 0;
		int totalScore = 0;
		int totalPar = 0;
		for (PlayerScore score : getLast5Rounds(currentWeek)) {
			totalScore += score.getScore();
			totalPar += score.getSide().getPar();
			count++;
		}
		if (count > 0) {
			hdcp = HandicapUtils.calculateHandicap(totalScore, totalPar, count);
		}

		return hdcp;
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Player) {
			Player othr = (Player) other;
			if (this.getId() != null && othr.getId() != null) {
				return this.getId().equals(othr.getId());
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return (getSkinsStartDate() != null ? "*" : "") + (getPlayerName() != null ? getPlayerName().toString() : "") + (getSeniorStartDate() != null ? " (S)" : "");
	}

	public int compareTo(Player other) {
		return new CompareToBuilder().append(this.getPlayerName().getLastName(), other.getPlayerName().getLastName()).append(this.getPlayerName().getFirstName(),
				other.getPlayerName().getFirstName()).toComparison();
	}

	public Set<PreRound> getPreRounds() {
		return preRounds;
	}

	public void setPreRounds(Set<PreRound> preRounds) {
		this.preRounds = preRounds;
	}

	public Integer getLowScore() {
		return lowScore;
	}

	public void setLowScore(Integer lowScore) {
		this.lowScore = lowScore;
	}

	public Integer getLowNet(Date seasonBeginDate) {
		Integer net = 99;

		for (WeeklyScore score : scores) {
			if (score.getNet() < net && score.getScoreDate().after(seasonBeginDate)) {
				net = score.getNet();
			}
		}
		return net;
	}

	public Integer getLowGross(Date seasonBeginDate) {
		Integer gross = 99;

		for (WeeklyScore score : scores) {
			if (score.getScore() < gross && score.getScoreDate().after(seasonBeginDate)) {
				gross = score.getScore();
			}
		}
		return gross;
	}

	public Double getIndividualPoints() {
		return individualPoints;
	}

	public void addIndividualPoints(Double points) {
		individualPoints = getIndividualPoints() + points;
	}

	public void setIndividualPoints(Double individualPoints) {
		this.individualPoints = individualPoints;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getSkinsStartDate() {
		return skinsStartDate;
	}

	public void setSkinsStartDate(Date skinsStartDate) {
		this.skinsStartDate = skinsStartDate;
	}

	public Date getSeniorStartDate() {
		return seniorStartDate;
	}

	public void setSeniorStartDate(Date seniorStartDate) {
		this.seniorStartDate = seniorStartDate;
	}

}
