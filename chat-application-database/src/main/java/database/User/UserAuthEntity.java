package database.User;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import chat_application_DTO.UserDTO.UserAuthPasswordDTO;

@Entity
public class UserAuthEntity {
	private long userId;
	private String password;
	private LocalDateTime lastChangePassword;
	
	public UserAuthEntity(UserAuthPasswordDTO user,long userID) {
		this.userId=userID;
		this.password=user.getPassword();
		this.lastChangePassword=user.getLastPasswordChange();
		
	}
	
	public UserAuthEntity() {
		
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getLastChangePassword() {
		return lastChangePassword;
	}
	public void setLastChangePassword(LocalDateTime lastChangePassword) {
		this.lastChangePassword = lastChangePassword;
	}
	
	
}
