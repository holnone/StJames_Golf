package com.stj.model;

public class GhostPlayer extends Player {
	private static final long serialVersionUID = 1L;

	public GhostPlayer() {
		setId(999999);
		setName(new Name("Ghost", "Player"));
	}
	
}
