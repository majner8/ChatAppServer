package authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import database.User.UserEntity;

@Component
@Scope("request")
public class RequestScopeAuthorizationValue {

	private UserEntity userEntity;
	
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	private String deviceID;
	
	
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
	
	
}
