package com.stj.model;

public class GhostPlayer extends Player {
	private static final long serialVersionUID = 1L;

	public GhostPlayer() {
		setId(999999);
		setPlayerName(new Name("Ghost", "Player"));
	}
	
}
