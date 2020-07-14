package com.stj.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Date;

public abstract class PlayerScore extends BaseEntity implements Comparable<PlayerScore> {

	private static final long serialVersionUID = 1L;

	private Player player;
	private Date scoreDate;
	private Side side;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public Date getScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(Date scoreDate) {
		this.scoreDate = scoreDate;
	}

	public int compareTo(PlayerScore other) {
		return new CompareToBuilder().append(this.getScoreDate(), other.getScoreDate()).toComparison();
	}

	public abstract Integer getScore();
}
