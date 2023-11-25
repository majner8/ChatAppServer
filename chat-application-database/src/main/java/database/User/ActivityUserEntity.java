package database.User;

import java.time.LocalDateTime;

import javax.persistence.Entity;

@Entity
public class ActivityUserEntity {

	private String deviceID;
	private long userID;
	private long primaryKey;
	private LocalDateTime login;
	private LocalDateTime logout;
	
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(long primaryKey) {
		this.primaryKey = primaryKey;
	}
	public LocalDateTime getLogin() {
		return login;
	}
	public void setLogin() {
		
	}
	public LocalDateTime getLogout() {
		return logout;
	}
	public void setLogout(LocalDateTime logout) {
		this.logout = logout;
	}
	
}
