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
	
}
