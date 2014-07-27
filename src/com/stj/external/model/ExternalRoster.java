package com.stj.external.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ExternalRoster implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementList
	List<ExternalPlayer> players;

	public List<ExternalPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<ExternalPlayer> players) {
		this.players = players;
	}

}
