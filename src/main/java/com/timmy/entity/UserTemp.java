package com.timmy.entity;

public class UserTemp {

	int enrollId;
	int admin;
	int backupnum;
	public int getEnrollId() {
		return enrollId;
	}
	public void setEnrollId(int enrollId) {
		this.enrollId = enrollId;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public int getBackupnum() {
		return backupnum;
	}
	public void setBackupnum(int backupnum) {
		this.backupnum = backupnum;
	}
	@Override
	public String toString() {
		return "UserTemp [enrollId=" + enrollId + ", admin=" + admin
				+ ", backupnum=" + backupnum + "]";
	}
	
	
}
