package starter.ThreadLocal;

import database.User.UserEntity;

public class ThreadLocalValue {

	private static final ThreadLocal<UserEntity> userEntity = new ThreadLocal<>();
	
	public static UserEntity getUserEntity() {
		return userEntity.get();
	}
	public static void setUserEntity(UserEntity user) {
		userEntity.set(user);
	}
	
	
	public static void clear() {
		userEntity.remove();
	}
	
}
