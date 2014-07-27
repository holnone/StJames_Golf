package com.stj.external.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Standings implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementList
	private List<ExternalTeam> round1Teams;

	@ElementList
	private List<ExternalTeam> round2Teams;

	public List<ExternalTeam> getRound1Teams() {
		return round1Teams;
	}

	public void setRound1Teams(List<ExternalTeam> round1Teams) {
		this.round1Teams = round1Teams;
	}

	public List<ExternalTeam> getRound2Teams() {
		return round2Teams;
	}

	public void setRound2Teams(List<ExternalTeam> round2Teams) {
		this.round2Teams = round2Teams;
	}

}
