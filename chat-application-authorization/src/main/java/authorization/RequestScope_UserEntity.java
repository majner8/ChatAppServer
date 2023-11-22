package authorization;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import database.User.UserEntity;

@Component
@RequestScope
public class RequestScope_UserEntity {

	private UserEntity userEntity;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}
