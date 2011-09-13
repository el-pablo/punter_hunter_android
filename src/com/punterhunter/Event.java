package com.punterhunter;

import java.util.Date;

import android.os.Message;

public class Event {

	private String name;
	private double longitude;
	private double latitude;
	private String venueName;
	private Date endTime;
	
	public Event(String name, double longitude, double latitude, String venueName, Date endTime) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.venueName = venueName;
		this.endTime = endTime;
	}

	public Event() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Event copy() {
		return new Event(name,longitude,latitude,venueName,endTime);
	}
	
	
	
}
