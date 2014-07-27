package com.stj.external.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.simpleframework.xml.Element;

public class ExternalMatch implements Serializable, Comparable<ExternalMatch> {

	private static final long serialVersionUID = 1L;

	@Element
	private Date date;

	@Element
	private Integer fntt1TeamNumber1;
	
	@Element
	private Integer fntt1TeamNumber2;
	
	@Element
	private Integer fntt2TeamNumber1;
	
	@Element
	private Integer fntt2TeamNumber2;
	
	@Element
	private Integer fntt3TeamNumber1;
	
	@Element
	private Integer fntt3TeamNumber2;

	@Element
	private Integer bntt1TeamNumber1;
	
	@Element
	private Integer bntt1TeamNumber2;
	
	@Element
	private Integer bntt2TeamNumber1;
	
	@Element
	private Integer bntt2TeamNumber2;
	
	@Element
	private Integer bntt3TeamNumber1;
	
	@Element
	private Integer bntt3TeamNumber2;

	@Override
	public int compareTo(ExternalMatch other) {
		return new CompareToBuilder().append(this.getDate(), other.getDate()).toComparison();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getFntt1TeamNumber1() {
		return fntt1TeamNumber1;
	}

	public void setFntt1TeamNumber1(Integer fntt1TeamNumber1) {
		this.fntt1TeamNumber1 = fntt1TeamNumber1;
	}

	public Integer getFntt1TeamNumber2() {
		return fntt1TeamNumber2;
	}

	public void setFntt1TeamNumber2(Integer fntt1TeamNumber2) {
		this.fntt1TeamNumber2 = fntt1TeamNumber2;
	}

	public Integer getFntt2TeamNumber1() {
		return fntt2TeamNumber1;
	}

	public void setFntt2TeamNumber1(Integer fntt2TeamNumber1) {
		this.fntt2TeamNumber1 = fntt2TeamNumber1;
	}

	public Integer getFntt2TeamNumber2() {
		return fntt2TeamNumber2;
	}

	public void setFntt2TeamNumber2(Integer fntt2TeamNumber2) {
		this.fntt2TeamNumber2 = fntt2TeamNumber2;
	}

	public Integer getFntt3TeamNumber1() {
		return fntt3TeamNumber1;
	}

	public void setFntt3TeamNumber1(Integer fntt3TeamNumber1) {
		this.fntt3TeamNumber1 = fntt3TeamNumber1;
	}

	public Integer getFntt3TeamNumber2() {
		return fntt3TeamNumber2;
	}

	public void setFntt3TeamNumber2(Integer fntt3TeamNumber2) {
		this.fntt3TeamNumber2 = fntt3TeamNumber2;
	}

	public Integer getBntt1TeamNumber1() {
		return bntt1TeamNumber1;
	}

	public void setBntt1TeamNumber1(Integer bntt1TeamNumber1) {
		this.bntt1TeamNumber1 = bntt1TeamNumber1;
	}

	public Integer getBntt1TeamNumber2() {
		return bntt1TeamNumber2;
	}

	public void setBntt1TeamNumber2(Integer bntt1TeamNumber2) {
		this.bntt1TeamNumber2 = bntt1TeamNumber2;
	}

	public Integer getBntt2TeamNumber1() {
		return bntt2TeamNumber1;
	}

	public void setBntt2TeamNumber1(Integer bntt2TeamNumber1) {
		this.bntt2TeamNumber1 = bntt2TeamNumber1;
	}

	public Integer getBntt2TeamNumber2() {
		return bntt2TeamNumber2;
	}

	public void setBntt2TeamNumber2(Integer bntt2TeamNumber2) {
		this.bntt2TeamNumber2 = bntt2TeamNumber2;
	}

	public Integer getBntt3TeamNumber1() {
		return bntt3TeamNumber1;
	}

	public void setBntt3TeamNumber1(Integer bntt3TeamNumber1) {
		this.bntt3TeamNumber1 = bntt3TeamNumber1;
	}

	public Integer getBntt3TeamNumber2() {
		return bntt3TeamNumber2;
	}

	public void setBntt3TeamNumber2(Integer bntt3TeamNumber2) {
		this.bntt3TeamNumber2 = bntt3TeamNumber2;
	}
}
