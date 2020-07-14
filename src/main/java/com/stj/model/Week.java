package com.stj.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Week extends BaseEntity implements Comparable<Week> {

	private static final long serialVersionUID = 1L;

	private Round round;
	private Date date;
	private TeamMatch frontNineTeeTime1;
	private TeamMatch frontNineTeeTime2;
	private TeamMatch frontNineTeeTime3;
	private TeamMatch backNineTeeTime1;
	private TeamMatch backNineTeeTime2;
	private TeamMatch backNineTeeTime3;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TeamMatch getFrontNineTeeTime1() {
		return frontNineTeeTime1;
	}

	public void setFrontNineTeeTime1(TeamMatch frontNineTeeTime1) {
		this.frontNineTeeTime1 = frontNineTeeTime1;
	}

	public TeamMatch getFrontNineTeeTime2() {
		return frontNineTeeTime2;
	}

	public void setFrontNineTeeTime2(TeamMatch frontNineTeeTime2) {
		this.frontNineTeeTime2 = frontNineTeeTime2;
	}

	public TeamMatch getBackNineTeeTime1() {
		return backNineTeeTime1;
	}

	public void setBackNineTeeTime1(TeamMatch backNineTeeTime1) {
		this.backNineTeeTime1 = backNineTeeTime1;
	}

	public TeamMatch getBackNineTeeTime2() {
		return backNineTeeTime2;
	}

	public void setBackNineTeeTime2(TeamMatch backNineTeeTime2) {
		this.backNineTeeTime2 = backNineTeeTime2;
	}

	public boolean hasFrontNineTeeTime1Team1() {
		return getFrontNineTeeTime1() != null
				&& !((getFrontNineTeeTime1().getTeam1() == null) | (getFrontNineTeeTime1().getTeam1().getTeam() == null));
	}

	public boolean hasFrontNineTeeTime1Team2() {
		return getFrontNineTeeTime1() != null
				&& !((getFrontNineTeeTime1().getTeam2() == null) | (getFrontNineTeeTime1().getTeam2().getTeam() == null));
	}

	public boolean hasFrontNineTeeTime2Team1() {
		return getFrontNineTeeTime2() != null
				&& !((getFrontNineTeeTime2().getTeam1() == null) | (getFrontNineTeeTime2().getTeam1().getTeam() == null));
	}

	public boolean hasFrontNineTeeTime2Team2() {
		return getFrontNineTeeTime2() != null
				&& !((getFrontNineTeeTime2().getTeam2() == null) | (getFrontNineTeeTime2().getTeam2().getTeam() == null));
	}

	public boolean hasFrontNineTeeTime3Team1() {
		return getFrontNineTeeTime3() != null
				&& !((getFrontNineTeeTime3().getTeam1() == null) | (getFrontNineTeeTime3().getTeam1().getTeam() == null));
	}

	public boolean hasFrontNineTeeTime3Team2() {
		return getFrontNineTeeTime3() != null
				&& !((getFrontNineTeeTime3().getTeam2() == null) | (getFrontNineTeeTime3().getTeam2().getTeam() == null));
	}

	public boolean hasBackNineTeeTime1Team1() {
		return getBackNineTeeTime1() != null && !((getBackNineTeeTime1().getTeam1() == null) | (getBackNineTeeTime1().getTeam1().getTeam() == null));
	}

	public boolean hasBackNineTeeTime1Team2() {
		return getBackNineTeeTime1() != null && !((getBackNineTeeTime1().getTeam2() == null) | (getBackNineTeeTime1().getTeam2().getTeam() == null));
	}

	public boolean hasBackNineTeeTime2Team1() {
		return getBackNineTeeTime2() != null && !((getBackNineTeeTime2().getTeam1() == null) | (getBackNineTeeTime2().getTeam1().getTeam() == null));
	}

	public boolean hasBackNineTeeTime2Team2() {
		return getBackNineTeeTime2() != null && !((getBackNineTeeTime2().getTeam2() == null) | (getBackNineTeeTime2().getTeam2().getTeam() == null));
	}

	public boolean hasBackNineTeeTime3Team1() {
		return getBackNineTeeTime3() != null && !((getBackNineTeeTime3().getTeam1() == null) | (getBackNineTeeTime3().getTeam1().getTeam() == null));
	}

	public boolean hasBackNineTeeTime3Team2() {
		return getBackNineTeeTime3() != null && !((getBackNineTeeTime3().getTeam2() == null) | (getBackNineTeeTime3().getTeam2().getTeam() == null));
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public int compareTo(Week other) {
		return new CompareToBuilder().append(this.getDate(), other.getDate()).toComparison();
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("MM-dd-yyyy").format(this.getDate());
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Week) {
			Week othr = (Week) other;
			if (this.getId() != null && othr.getId() != null) {
				return this.getId().equals(othr.getId());
			} else {
				return false;
			}
		}
		return false;
	}

	public TeamMatch getFrontNineTeeTime3() {
		return frontNineTeeTime3;
	}

	public void setFrontNineTeeTime3(TeamMatch frontNineTeeTime3) {
		this.frontNineTeeTime3 = frontNineTeeTime3;
	}

	public TeamMatch getBackNineTeeTime3() {
		return backNineTeeTime3;
	}

	public void setBackNineTeeTime3(TeamMatch backNineTeeTime3) {
		this.backNineTeeTime3 = backNineTeeTime3;
	}

}
