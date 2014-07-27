package com.stj.external.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.stj.util.HandicapUtils;

@Root
public class ExternalPlayer implements Serializable, Comparable<ExternalPlayer> {

	private static final long serialVersionUID = -1L;

	@Element
	private String firstName;

	@Element
	private String lastName;

	@Element(required=false)
	private ExternalTeam team;

	@ElementList(required=false)
	private List<Score> scores = new ArrayList<Score>();

	@Element(required=false)
	private Integer currentHandicap;

	public ExternalTeam getTeam() {
		return team;
	}

	public void setTeam(ExternalTeam team) {
		this.team = team;
	}

	public List<Score> getLast5Rounds(Date currentWeek) {
		List<Score> list = new ArrayList<Score>(getScores());
		Collections.sort(list);
		Collections.reverse(list);

		if (currentWeek != null) {
			for (Iterator<Score> iter = list.iterator(); iter.hasNext();) {
				Score score = iter.next();
				if (!score.getScoreDate().before(currentWeek)) {
					iter.remove();
				}
			}
		}

		List<Score> last5Rounds = new ArrayList<Score>();
		if (list.size() > 4) {
			last5Rounds = new ArrayList<Score>(list.subList(0, 5));
		} else if (list.size() > 0) {
			last5Rounds = new ArrayList<Score>(list.subList(0, list.size()));
		}
		Collections.reverse(last5Rounds);
		return last5Rounds;
	}

	public String getLast5Rounds_String() {
		StringBuffer buffer = new StringBuffer();
		for (Score score : getLast5Rounds(null)) {
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
		for (Score score : getLast5Rounds(currentWeek)) {
			totalScore += score.getScore();
			totalPar += score.getPar();
			count++;
		}
		if (count > 0) {
			hdcp = HandicapUtils.calculateHandicap(totalScore, totalPar, count);
		}

		return hdcp;
	}

	public Integer getCurrentHandicap() {
		return currentHandicap;
	}

	public void setCurrentHandicap(Integer currentHandicap) {
		this.currentHandicap = currentHandicap;
	}

	public String toString() {
		return lastName != null ? lastName + ", " + firstName : "";
	}

	public int compareTo(ExternalPlayer other) {
		return new CompareToBuilder().append(this.getLastName(), other.getLastName()).append(this.getFirstName(), other.getFirstName())
				.toComparison();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

}
