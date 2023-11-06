package chat_application_database.AuthorizationEntity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity(name = LoginActivityEntity.loginActivityEntityTable)
public class LoginActivityEntity {

    public static final String loginActivityEntityTable = "some_name";  // You need to provide a proper name
    public static final String loginTimeName = "login_Time";
    public static final String logoutTimeName = "logout_Time";
    public static final String PrimaryKeyName = "primary_key";
    public static final String deviceIdName = "device";
    public static final String userIdName="userId";
   
    @Column(name = loginTimeName)
    private LocalDateTime loginTime;

    @Column(name = logoutTimeName)
    private LocalDateTime logoutTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne
    @JoinColumn(name = Htpp_Request_Login_Entity.Htpp_Request_Login_Entity_PrimaryKeyName)
    @Column(name = PrimaryKeyName)
    private long primaryKey;

    @Column(name=LoginActivityEntity.deviceIdName)
    private String deviceId;
    @Column(name=LoginActivityEntity.userIdName)
    private String userId;


	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}

	public long getPrimaryKey() {
		return primaryKey;
	}


    // ... getters and setters ...
    
    
}

