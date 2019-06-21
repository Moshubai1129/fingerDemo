package com.timmy.entity;

public class UserInfo {

	int enrollId;
	String name;
	int backupnum;
	int admin;
	String record;
	public int getEnrollId() {
		return enrollId;
	}
	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBackupnum() {
		return backupnum;
	}
	public void setBackupnum(int backupnum) {
		this.backupnum = backupnum;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	@Override
	public String toString() {
		return "UserInfo [enrollId=" + enrollId + ", name=" + name
				+ ", backupnum=" + backupnum + ", admin=" + admin + ", record="
				+ record + "]";
	}
	
	
}
