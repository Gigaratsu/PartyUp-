package com.benjaminboyce.partyup;

public class PartyMember {

	private String id;
	private String name;
	private String number;
	private String status;
	private Double lat;
	private Double lon;
	
	public static final String PAIRED = "paired";
	public static final String REQUESTING = "requesting";
	public static final String DENIED = "denied";
	
	public PartyMember() {
		id = "";
		name = "";
		number = "";
		status = REQUESTING;
		lat = 0.00;
		lon = 0.00;
	}
	
	public PartyMember(PartyMember member){
		id = member.getId();
		name = member.getName();
		number = member.getNumber();
		status = member.getStatus();
		lat = member.getLat();
		lon = member.getLon();
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

}
