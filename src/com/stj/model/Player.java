package com.stj.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.stj.util.HandicapUtils;

public class Player extends BaseEntity implements Comparable<Player> {

	private static final long serialVersionUID = 1L;

	private Name name;
	private Team team;
	private Set<WeeklyScore> scores = new HashSet<WeeklyScore>();
	private Set<PreRound> preRounds = new HashSet<PreRound>();
	private Integer lowScore;
	private boolean active;
	private Date skinsStartDate;

	private Double individualPoints = new Double(0.0);

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
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

		if (currentWeek != null) {
			for (Iterator<PlayerScore> iter = list.iterator(); iter.hasNext();) {
				PlayerScore score = iter.next();
				if (!score.getScoreDate().before(currentWeek)) {
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
		return (getSkinsStartDate() != null ? "*" : "") + (getName() != null ? getName().toString() : "");
	}

	public int compareTo(Player other) {
		return new CompareToBuilder().append(this.getName().getLastName(), other.getName().getLastName()).append(this.getName().getFirstName(),
				other.getName().getFirstName()).toComparison();
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

}
