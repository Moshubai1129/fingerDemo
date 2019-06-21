package com.timmy.entity;

import java.util.Date;

public class Records {
	
    int id;
	int enrollId;
	String recordsTime;
	int mode;
	int intout;
	int event;
	String deviceSerialNum;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEnrollId() {
		return enrollId;
	}
	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}
	public String getRecordsTime() {
		return recordsTime;
	}
	public void setRecordsTime(String recordTime) {
		this.recordsTime = recordTime;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getIntout() {
		return intout;
	}
	public void setIntout(int intout) {
		this.intout = intout;
	}
	public int getEvent() {
		return event;
	}
	public void setEvent(int event) {
		this.event = event;
	}
	public String getDeviceSerialNum() {
		return deviceSerialNum;
	}
	public void setDeviceSerialNum(String deviceSerialNum) {
		this.deviceSerialNum = deviceSerialNum;
	}
	@Override
	public String toString() {
		return "Records [id=" + id + ", enrollId=" + enrollId
				+ ", recordsTime=" + recordsTime + ", mode=" + mode
				+ ", intout=" + intout + ", event=" + event
				+ ", deviceSerialNum=" + deviceSerialNum + "]";
	}

	
	
}
