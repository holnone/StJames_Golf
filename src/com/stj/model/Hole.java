package com.stj.model;

public class Hole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer holeNumber;
	private Integer yardage;
	private Integer par;
	private Integer handicap;

	public Hole() {
	}

	public Hole(Integer holeNumber, Integer yardage, Integer par, Integer handicap) {
		this.holeNumber = holeNumber;
		this.yardage = yardage;
		this.par = par;
		this.handicap = handicap;
	}
	
	public Integer getHandicap() {
		return handicap;
	}

	public void setHandicap(Integer handicap) {
		this.handicap = handicap;
	}

	public Integer getYardage() {
		return yardage;
	}

	public void setYardage(Integer yardage) {
		this.yardage = yardage;
	}

	public Integer getPar() {
		return par;
	}

	public void setPar(Integer par) {
		this.par = par;
	}

	public Integer getHoleNumber() {
		return holeNumber;
	}

	public void setHoleNumber(Integer holeNumber) {
		this.holeNumber = holeNumber;
	}

	@Override
	public String toString() {
		return "#" + holeNumber + "; yards - " + getYardage() + "; par - " + getPar() + "; hdcp - " + getHandicap();
	}

}
