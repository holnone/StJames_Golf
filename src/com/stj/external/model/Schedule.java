package com.stj.external.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Schedule implements Serializable {
	private static final long serialVersionUID = -1L;

	@Element
	private String frontNineTeeTime1;
	
	@Element
	private String frontNineTeeTime2;
	
	@Element
	private String frontNineTeeTime3;

	@Element
	private String backNineTeeTime1;
	
	@Element
	private String backNineTeeTime2;
	
	@Element
	private String backNineTeeTime3;

	@ElementList
	private List<ExternalMatch> matches;

	public List<ExternalMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<ExternalMatch> matches) {
		this.matches = matches;
	}

	public String getFrontNineTeeTime1() {
		return frontNineTeeTime1;
	}

	public void setFrontNineTeeTime1(String frontNineTeeTime1) {
		this.frontNineTeeTime1 = frontNineTeeTime1;
	}

	public String getFrontNineTeeTime2() {
		return frontNineTeeTime2;
	}

	public void setFrontNineTeeTime2(String frontNineTeeTime2) {
		this.frontNineTeeTime2 = frontNineTeeTime2;
	}

	public String getFrontNineTeeTime3() {
		return frontNineTeeTime3;
	}

	public void setFrontNineTeeTime3(String frontNineTeeTime3) {
		this.frontNineTeeTime3 = frontNineTeeTime3;
	}

	public String getBackNineTeeTime1() {
		return backNineTeeTime1;
	}

	public void setBackNineTeeTime1(String backNineTeeTime1) {
		this.backNineTeeTime1 = backNineTeeTime1;
	}

	public String getBackNineTeeTime2() {
		return backNineTeeTime2;
	}

	public void setBackNineTeeTime2(String backNineTeeTime2) {
		this.backNineTeeTime2 = backNineTeeTime2;
	}

	public String getBackNineTeeTime3() {
		return backNineTeeTime3;
	}

	public void setBackNineTeeTime3(String backNineTeeTime3) {
		this.backNineTeeTime3 = backNineTeeTime3;
	}

}
