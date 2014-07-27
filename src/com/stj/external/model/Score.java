package com.stj.external.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.simpleframework.xml.Element;

public class Score implements Serializable, Comparable<Score> {

	private static final long serialVersionUID = -1L;

	@Element
	private Integer score;

	@Element
	private Date scoreDate;

	@Element
	private Integer par;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(Date scoreDate) {
		this.scoreDate = scoreDate;
	}

	@Override
	public int compareTo(Score other) {
		return new CompareToBuilder().append(this.getScoreDate(), other.getScoreDate()).toComparison();
	}

	public Integer getPar() {
		return par;
	}

	public void setPar(Integer par) {
		this.par = par;
	}

}
